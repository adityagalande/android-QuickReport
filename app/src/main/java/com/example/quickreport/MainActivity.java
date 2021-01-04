package com.example.quickreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String SAMPLE_JSON_RESPONSE ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=2.5&limit=150";
    private EarthquakeAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.earthquakeList);
        mAdapter = new EarthquakeAdapter(this, new ArrayList<EarthquakeData>());

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EarthquakeData urlData = (EarthquakeData) mAdapter.getItem(position);

                Intent URL = new Intent(Intent.ACTION_VIEW);
                URL.setData(Uri.parse(urlData.getUrl()));
                startActivity(URL);
            }
        });

        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(SAMPLE_JSON_RESPONSE);
    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<EarthquakeData>>{

        @Override
        protected ArrayList<EarthquakeData> doInBackground(String... urls) {

            if(urls.length < 1 ){
                return null;
            }
            ArrayList<EarthquakeData> result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }

        protected void onPostExecute(ArrayList<EarthquakeData> data){
            mAdapter.clear();

            if(data != null && !data.isEmpty()){
                mAdapter.addAll(data);
            }
        }
    }
}