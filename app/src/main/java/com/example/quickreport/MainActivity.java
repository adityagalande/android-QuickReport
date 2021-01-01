package com.example.quickreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String SAMPLE_JSON_RESPONSE ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<EarthquakeData> earthquakes = QueryUtils.extractEarthquakes();

        ListView listView = (ListView) findViewById(R.id.earthquakeList);

        EarthquakeAdapter earthquakeArray = new EarthquakeAdapter(this, earthquakes);
        listView.setAdapter(earthquakeArray);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EarthquakeData urlData = earthquakes.get(position);

                Intent URL = new Intent(Intent.ACTION_VIEW);
                URL.setData(Uri.parse(urlData.getUrl()));
                startActivity(URL);
            }
        });
    }
}