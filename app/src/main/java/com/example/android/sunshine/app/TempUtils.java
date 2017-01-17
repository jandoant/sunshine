package com.example.android.sunshine.app;

import android.content.Context;

/**
 * Created by Jan on 17.01.2017.
 */

public class TempUtils {

    public static String formatTemperature(Context context, double temperature, boolean isMetric) {

        double temp;
        String symbol = "\u00B0";
        if (isMetric) {
            //Celcius
            symbol += "C";
            temp = temperature;
        } else {
            //Fahrenheit
            symbol += "F";
            temp = 9 * temperature / 5 + 32;
        }

        return context.getString(R.string.format_temperature, temp, symbol);
    }
}
