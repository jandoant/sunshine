package com.example.android.sunshine.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_main);

        //UX for displaying Summary of Settings
        bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_location_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_units_key)));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {

        preference.setOnPreferenceChangeListener(this);
        //auf Pref-File dieser App zugreifen
        SharedPreferences preferencesFile = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
        //den aktuellen im File gespeicherten Wert auslesen
        String preferenceValue = preferencesFile.getString(preference.getKey(), "");
        //Summary setzen
        onPreferenceChange(preference, preferenceValue);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String summaryString = newValue.toString();

        //wenn es sich um eine ListPrefernce handelt sollen die Labels als summary angezeigt werden, nicht die Values
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(summaryString);
            if (prefIndex >= 0) {
                CharSequence[] labels = listPreference.getEntries();
                preference.setSummary(labels[prefIndex]);
            }
        } else {
            preference.setSummary(summaryString);
        }
        return true;
    }
}
