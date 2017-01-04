package com.example.android.sunshine.app;

import android.content.Context;
import android.support.annotation.NonNull;
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

    public ForecastAdapter(Context context, List<Forecast> data) {
        super(context, 0, data);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        //Data to populate
        Forecast forecast = getItem(position);

        //View to create
        View listItem = convertView;

        //Inflate Item Root Layout
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            listItem = inflater.inflate(R.layout.list_item_forecast, parent, false);
        }

        //connect Views to XML
        TextView txt_description = (TextView) listItem.findViewById(R.id.txt_description);
        TextView txt_temperature_max = (TextView) listItem.findViewById(R.id.txt_temperature_max);
        TextView txt_temperature_min = (TextView) listItem.findViewById(R.id.txt_temperature_min);

        //put data into Views
        if (forecast != null) {
            txt_description.setText(forecast.getWeather().getDescription());
            txt_temperature_max.setText(String.valueOf(forecast.getTemperature(Forecast.TEMPERATURE_DAY)));
            txt_temperature_min.setText(String.valueOf(forecast.getTemperature(Forecast.TEMPERATURE_MIN)));
        }

        return listItem;
    }
}
