package com.example.android.sunshine.app;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ForecastListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String OPEN_WEATHER_MAP_API_KEY = "0520e2af6d51ebc90609853be2970c0b";
    private static final String BASE_QUERY_URL_DAILY_FORECAST = "http://api.openweathermap.org/data/2.5/forecast/daily?";

    ForecastAdapter adapter;

    ProgressBar loadingIndicator;

    public ForecastListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        //Adapter
        adapter = new ForecastAdapter(getActivity(), new ArrayList<Forecast>());
    }

    private String buildQueryURL(String postalcode) {

        Uri baseUri = Uri.parse(BASE_QUERY_URL_DAILY_FORECAST);

        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("appid", OPEN_WEATHER_MAP_API_KEY);
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("q", postalcode);
        uriBuilder.appendQueryParameter("cnt", "7");
        uriBuilder.appendQueryParameter("units", "metric");

        return uriBuilder.toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.list_forecast);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        loadingIndicator = (ProgressBar) rootView.findViewById(R.id.loading_indicator);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reloadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.forecast_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.settings_refresh:
                adapter.clear();
                reloadData();
                break;
        }//Ende switch

        return super.onOptionsItemSelected(item);
    }

    private void reloadData() {
        //AsyncTask
        loadingIndicator.setVisibility(View.VISIBLE);
        ForecastAsyncTask forecastAsyncTask = new ForecastAsyncTask();
        forecastAsyncTask.execute(buildQueryURL("09125,de"));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        openDetailActivity(adapter.getItem(position));
    }

    private void openDetailActivity(Forecast detailForecast) {
        Intent intent = new Intent(getActivity(), ForecastDetailActivity.class);
        intent.putExtra("detail", detailForecast.toString());
        startActivity(intent);
    }

    private class ForecastAsyncTask extends AsyncTask<String, Void, ArrayList<Forecast>> {

        @Override
        protected ArrayList<Forecast> doInBackground(String... queryURL) {
            return ForecastRequest.fetchEarthquakeData(queryURL[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Forecast> result) {
            updateUI(result);
            loadingIndicator.setVisibility(View.GONE);
        }

        private void updateUI(ArrayList<Forecast> result) {
                /*
                If there is a valid list of {@link Earthquake}s, then add them to the adapter's
                data set. This will trigger the ListView to update
                */
            if (result != null && !result.isEmpty()) {
                adapter.clear();
                for (Forecast forecast : result) {
                    adapter.add(forecast);
                }
            }
        }
    }//ENDE ASYNCTASK
}//ENDE FRAGMENT

