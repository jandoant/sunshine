package com.example.android.sunshine.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        TextView txt_temperature_day = (TextView) listItem.findViewById(R.id.txt_temperature_day);
        TextView txt_humidity = (TextView) listItem.findViewById(R.id.txt_humidity);

        //put data into Views
        if (forecast != null) {
            txt_description.setText(forecast.getDescription());
            txt_temperature_day.setText(String.valueOf(forecast.getTemperatureDay()));
            txt_humidity.setText(String.valueOf(forecast.getHumidity()));
        }

        return listItem;
    }
}
