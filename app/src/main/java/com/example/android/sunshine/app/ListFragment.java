package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.android.sunshine.app.models.Forecast;

import java.util.ArrayList;

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener, android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Forecast>> {
    private static final String OPEN_WEATHER_MAP_API_KEY = "0520e2af6d51ebc90609853be2970c0b";
    private static final String BASE_QUERY_URL_DAILY_FORECAST = "http://api.openweathermap.org/data/2.5/forecast/daily?";

    private static final int LOADER_ID = 0;

    ForecastAdapter adapter;
    ProgressBar loadingIndicator;

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        //leerer Adapter
        adapter = new ForecastAdapter(getActivity(), new ArrayList<Forecast>());
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.list_forecast);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        loadingIndicator = (ProgressBar) rootView.findViewById(R.id.loading_indicator);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings_refresh:
                getLoaderManager().restartLoader(LOADER_ID, null, this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        openDetailActivity(adapter.getItem(position));
    }

    private void openDetailActivity(Forecast detailForecast) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("detail", detailForecast.toString());
        startActivity(intent);
    }

    @Override
    public Loader<ArrayList<Forecast>> onCreateLoader(int id, Bundle args) {
        return new ForecastLoader(getActivity(), buildQueryURL());
    }

    private String buildQueryURL() {

        SharedPreferences prefFile = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //Auslesen der aktuell gespeicherten Settings, falls noch nix gespeichert, dann default Werte verwenden
        String locationSetting = prefFile.getString(getString(R.string.settings_location_key), getString(R.string.settings_location_default_value));
        String unitSetting = prefFile.getString(getString(R.string.settings_units_key), getString(R.string.settings_units_default_value));

        //Zusammensetzen der Url
        Uri baseUri = Uri.parse(BASE_QUERY_URL_DAILY_FORECAST);

        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("appid", OPEN_WEATHER_MAP_API_KEY);
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("q", locationSetting);
        uriBuilder.appendQueryParameter("cnt", "7");
        uriBuilder.appendQueryParameter("units", "metric");

        Log.d("TAG1", uriBuilder.toString());

        return uriBuilder.toString();
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Forecast>> loader, ArrayList<Forecast> result) {
        updateUI(result);
        Log.d("TAG1", "onLoadFinished");
        loadingIndicator.setVisibility(View.GONE);
    }

    private void updateUI(ArrayList<Forecast> result) {
                /*
                If there is a valid list of {@link Forecast}s, then add them to the adapter's
                data set. This will trigger the ListView to update
                */
        if (result != null && !result.isEmpty()) {
            adapter.clear();
            adapter.addAll(result);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Forecast>> loader) {
        adapter.clear();
    }
}//ENDE FRAGMENT

