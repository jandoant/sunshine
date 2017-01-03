package com.example.android.sunshine.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private static final String OPEN_WEATHER_MAP_API_KEY = "0520e2af6d51ebc90609853be2970c0b";

    private static final String BASE_QUERY_URL_DAILY_FORECAST = "http://api.openweathermap.org/data/2.5/forecast/daily?";

    //hardcoded Query
    private static final String QUERY_WEATHER = "http://api.openweathermap.org/data/2.5/forecast/daily?appid=0520e2af6d51ebc90609853be2970c0b&format=json&cnt=7&q=94043,us&units=metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        ForecastAdapter adapter;

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //Adapter
            adapter = new ForecastAdapter(getActivity(), new ArrayList<Forecast>());
            ForecastAsyncTask forecastAsyncTask = new ForecastAsyncTask();
            forecastAsyncTask.execute(QUERY_WEATHER);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //init UI
            ListView listView = (ListView) rootView.findViewById(R.id.list_forecast);
            listView.setAdapter(adapter);
            return rootView;
        }

        private void updateUI(ArrayList<Forecast> result) {
            /*
        If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        data set. This will trigger the ListView to update
        */

            if (result != null && !result.isEmpty()) {
                adapter.clear();
                //add all
                //ListView will update automatically
                for (Forecast forecast : result) {
                    adapter.add(forecast);
                }
            }
        }

        private class ForecastAsyncTask extends AsyncTask<String, Void, ArrayList<Forecast>> {

            @Override
            protected ArrayList<Forecast> doInBackground(String... queryURL) {

                ArrayList<Forecast> result = ForecastRequest.fetchEarthquakeData(queryURL[0]);
                return result;
            }

            @Override
            protected void onPostExecute(ArrayList<Forecast> result) {
                updateUI(result);
            }
        }
    }
}
