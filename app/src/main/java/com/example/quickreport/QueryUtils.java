package com.example.quickreport;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QueryUtils {

    public static ArrayList<EarthquakeData> fetchEarthquakeData(String requestUrl) {
        URL url = createURL(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<EarthquakeData> earthquakeData = extractFromJson(jsonResponse);
        return earthquakeData;
    }

    private static URL createURL(String urls){
        URL url = null;
        try {
            url = new URL(urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e("Error TAG.","NOPE!!");
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }finally {
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder stringBuilderOutput = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = br.readLine();
            while (line != null){
                stringBuilderOutput.append(line);
                line = br.readLine();
            }
        }
        return stringBuilderOutput.toString();
    }

    private static ArrayList<EarthquakeData> extractFromJson(String earthquakeJson){
        if(TextUtils.isEmpty(earthquakeJson)){
            return null;
        }

        ArrayList<EarthquakeData> earthquakeData = new ArrayList<>();
        try{
            JSONObject baseJsonObject = new JSONObject(earthquakeJson);
            JSONArray earthquakeArray = baseJsonObject.getJSONArray("features");

            for (int i = 0; i < earthquakeArray.length(); i++) {
                JSONObject currentJSONObject = earthquakeArray.getJSONObject(i);
                JSONObject propertiesJSONObject = currentJSONObject.getJSONObject("properties");
                double magnitude = propertiesJSONObject.getDouble("mag");
                String location = propertiesJSONObject.getString("place");
                long times = propertiesJSONObject.getLong("time");

                String date = getDate(times, "dd/MMM/yyyy");
                String time = getDate(times, "h:mm a");
                String url = propertiesJSONObject.getString("url");

                EarthquakeData earthquake = new EarthquakeData(magnitude, location, date, time, url);
                earthquakeData.add(earthquake);
            }
            return earthquakeData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return earthquakeData;
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
