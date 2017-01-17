package com.example.android.sunshine.app;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.sunshine.app.models.Forecast;

import java.util.ArrayList;

/**
 * Created by Jan on 17.01.2017.
 */

public class ForecastLoader extends AsyncTaskLoader<ArrayList<Forecast>> {
    private static final String LOG_TAG = ForecastLoader.class.getName();
    private final String queryURL;

    public ForecastLoader(Context context, String url) {
        super(context);
        this.queryURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Forecast> loadInBackground() {
        if (queryURL == "") {
            Log.e(LOG_TAG, "Die URL des Queries ist nicht vorhanden oder leer");
            return null;
        } else {
            ArrayList<Forecast> result = ForecastRequest.fetchForecastData(queryURL);
            return result;
        }
    }
}
