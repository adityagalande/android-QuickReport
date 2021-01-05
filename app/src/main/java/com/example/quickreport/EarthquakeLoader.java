package com.example.quickreport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeData>> {

    private String mUrl;

    public EarthquakeLoader(Context context, String Url) {
        super(context);
        mUrl = Url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<EarthquakeData> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<EarthquakeData> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}
