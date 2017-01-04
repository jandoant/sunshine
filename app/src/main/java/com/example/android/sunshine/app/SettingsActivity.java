package com.example.android.sunshine.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //add Settings Fragment to Activity
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.settings_container, new SettingsFragment()).commit();
        }
    }
}
