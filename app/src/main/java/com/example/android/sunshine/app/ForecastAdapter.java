package com.example.android.sunshine.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.sunshine.app.models.Forecast;

import java.util.List;

/**
 * Created by Jan on 03.01.2017.
 */

public class ForecastAdapter extends ArrayAdapter<Forecast> {

    private boolean isMetric;

    public ForecastAdapter(Context context, List<Forecast> data) {
        super(context, 0, data);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        //Data to populate
        Forecast forecast = getItem(position);
        //Temperature Units
        checkUnits();

        //View to create
        View listItemView = convertView;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        //Inflate Item Root Layout
        if (convertView == null) {
            switch (position) {
                case 0:
                    listItemView = inflater.inflate(R.layout.list_item_forecast_today, parent, false);
                    break;
                default:
                    listItemView = inflater.inflate(R.layout.list_item_forecast, parent, false);
                    break;
            }//Ende switch
        }

        ViewHolder viewHolder = new ViewHolder(listItemView);

        //put data into Views
        if (forecast != null) {

            long dateInMillis = forecast.getDateMilliseconds();
            double temperatureMax = forecast.getTemperature(Forecast.TEMPERATURE_MAX);
            double temperatureMin = forecast.getTemperature(Forecast.TEMPERATURE_MIN);

            viewHolder.txt_date.setText(DateUtils.getFriendlyDayString(getContext(), dateInMillis));
            viewHolder.txt_description.setText(forecast.getWeather().getDescription());
            viewHolder.txt_temperature_max.setText(TempUtils.formatTemperature(getContext(), temperatureMax, isMetric));
            viewHolder.txt_temperature_min.setText(TempUtils.formatTemperature(getContext(), temperatureMin, isMetric));
        }

        return listItemView;
    }

    private void checkUnits() {
        SharedPreferences prefFile = PreferenceManager.getDefaultSharedPreferences(getContext());
        String unitSetting = prefFile.getString(getContext().getString(R.string.settings_units_key), getContext().getString(R.string.settings_units_default_value));

        Log.d("TAG1", unitSetting);

        if (unitSetting.equals("metric")) {
            isMetric = true;
            Log.d("TAG1", String.valueOf(isMetric));
        } else {
            isMetric = false;
            Log.d("TAG1", String.valueOf(isMetric));
        }
    }

    public static class ViewHolder {

        public final TextView txt_date;
        public final TextView txt_description;
        public final TextView txt_temperature_max;
        public final TextView txt_temperature_min;

        public ViewHolder(View view) {

            this.txt_date = (TextView) view.findViewById(R.id.txt_date);
            this.txt_description = (TextView) view.findViewById(R.id.txt_description);
            this.txt_temperature_max = (TextView) view.findViewById(R.id.txt_temperature_max);
            this.txt_temperature_min = (TextView) view.findViewById(R.id.txt_temperature_min);
        }
    }
}
