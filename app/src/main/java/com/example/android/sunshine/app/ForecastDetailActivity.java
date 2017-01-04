package com.example.android.sunshine.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ForecastDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_detail);

        if (savedInstanceState == null) {
            ForecastDetailFragment forecastDetailFragment = new ForecastDetailFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.detail_container, forecastDetailFragment).commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
