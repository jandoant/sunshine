package com.example.android.sunshine.app;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForecastRequest {

    public static ArrayList<Forecast> fetchEarthquakeData(String queryURL) {

        ArrayList<Forecast> result;

        String jsonResponse = "{\"city\":{\"id\":5375480,\"name\":\"Mountain View\",\"coord\":{\"lon\":-122.083847,\"lat\":37.386051},\"country\":\"US\",\"population\":0},\"cod\":\"200\",\"message\":0.0148,\"cnt\":7,\"list\":[{\"dt\":1483387200,\"temp\":{\"day\":8,\"min\":8,\"max\":8,\"night\":8,\"eve\":8,\"morn\":8},\"pressure\":992.7,\"humidity\":100,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":1.66,\"deg\":131,\"clouds\":92,\"rain\":1.19},{\"dt\":1483473600,\"temp\":{\"day\":9.42,\"min\":7.59,\"max\":11.66,\"night\":11.66,\"eve\":10.3,\"morn\":7.59},\"pressure\":989.6,\"humidity\":98,\"weather\":[{\"id\":502,\"main\":\"Rain\",\"description\":\"heavy intensity rain\",\"icon\":\"10d\"}],\"speed\":4.59,\"deg\":165,\"clouds\":92,\"rain\":22.23},{\"dt\":1483560000,\"temp\":{\"day\":13.46,\"min\":2.98,\"max\":13.46,\"night\":2.98,\"eve\":8.88,\"morn\":12.66},\"pressure\":986.24,\"humidity\":99,\"weather\":[{\"id\":502,\"main\":\"Rain\",\"description\":\"heavy intensity rain\",\"icon\":\"10d\"}],\"speed\":5.06,\"deg\":210,\"clouds\":92,\"rain\":14.19},{\"dt\":1483646400,\"temp\":{\"day\":13.43,\"min\":13.04,\"max\":14.21,\"night\":13.26,\"eve\":14.21,\"morn\":13.04},\"pressure\":1005.21,\"humidity\":0,\"weather\":[{\"id\":502,\"main\":\"Rain\",\"description\":\"heavy intensity rain\",\"icon\":\"10d\"}],\"speed\":7.5,\"deg\":164,\"clouds\":95,\"rain\":13.8},{\"dt\":1483732800,\"temp\":{\"day\":13.15,\"min\":12.1,\"max\":13.15,\"night\":12.1,\"eve\":13.11,\"morn\":12.65},\"pressure\":1003.79,\"humidity\":0,\"weather\":[{\"id\":502,\"main\":\"Rain\",\"description\":\"heavy intensity rain\",\"icon\":\"10d\"}],\"speed\":6.12,\"deg\":181,\"clouds\":99,\"rain\":13.29},{\"dt\":1483819200,\"temp\":{\"day\":11.68,\"min\":8.86,\"max\":12.41,\"night\":8.86,\"eve\":12.41,\"morn\":11.66},\"pressure\":1012.4,\"humidity\":0,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"speed\":1.22,\"deg\":223,\"clouds\":71,\"rain\":3.4},{\"dt\":1483905600,\"temp\":{\"day\":9.76,\"min\":7.63,\"max\":10.64,\"night\":7.63,\"eve\":10.64,\"morn\":9.26},\"pressure\":1012.22,\"humidity\":0,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"speed\":2.32,\"deg\":60,\"clouds\":100,\"rain\":9.76}]}";

        result = createForecastListFromJson(jsonResponse);
        return result;
    }

    private static ArrayList<Forecast> createForecastListFromJson(String jsonResponse) {

        ArrayList<Forecast> forecastList = new ArrayList<>();

        //wenn json String leer ist -> leere Liste
        if (jsonResponse == "") {
            return null;
        }

        JSONObject root;
        try {
            root = new JSONObject(jsonResponse);
            JSONArray forecastArr = root.getJSONArray("list");
            for (int i = 0; i < forecastArr.length(); i++) {
                JSONObject forecastObject = forecastArr.getJSONObject(i);
                Forecast forecast = extractForecastFromJSONObject(forecastObject);
                forecastList.add(forecast);
            }
        } catch (JSONException e) {
            Log.e("TAG", "Json-String ist nich valide", e);
            return null;
        }

        return forecastList;
    }

    private static Forecast extractForecastFromJSONObject(JSONObject forecastObject) throws JSONException {

        //WEATHER
        JSONArray weather = forecastObject.getJSONArray("weather");
        //--description
        String description = weather.getJSONObject(0).getString("description");
        //HUMIDITY
        int humidity = forecastObject.getInt("humidity");
        //TEMPERATURE
        JSONObject temperature = forecastObject.getJSONObject("temp");
        //--day
        int temperature_day = temperature.getInt("day");

        return new Forecast(description, humidity, temperature_day);
    }
}
