package com.example.android.sunshine.app;

import android.util.Log;

import com.example.android.sunshine.app.models.Forecast;
import com.example.android.sunshine.app.models.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ForecastRequest {

    public static ArrayList<Forecast> fetchForecastData(String queryURL) {

        ArrayList<Forecast> result;

        URL url = createURL(queryURL);

        if (url == null) {
            return null;
        }

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        result = createForecastListFromJson(jsonResponse);
        return result;
    }

    private static URL createURL(String queryURL) {

        if (queryURL == "") {
            return null;
        }

        try {
            return new URL(queryURL);
        } catch (MalformedURLException e) {
            Log.e("TAG", "Invalid URL", e);
            return null;
        }
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        InputStream inputStream = null;

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            } else {
                Log.e("LOG", "Your HttpRequest was not successfull. ERROR CODE: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("LOG", "An Error occured while establishing your HttpConnection. ERROR CODE: ", e);
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
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
            JSONArray listArr = root.getJSONArray("list");
            for (int i = 0; i < listArr.length(); i++) {
                JSONObject forecastObject = listArr.getJSONObject(i);
                Forecast forecast = extractSingleForecastFromJSONObject(forecastObject);
                forecastList.add(forecast);
            }
        } catch (JSONException e) {
            Log.e("TAG", "Json-String ist nich valide", e);
            return null;
        }

        return forecastList;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String line = bufferedReader.readLine();
        while (line != null) {
            output.append(line);
            line = bufferedReader.readLine();
        }

        return output.toString();
    }

    private static Forecast extractSingleForecastFromJSONObject(JSONObject forecastObject) throws JSONException {

        JSONObject weatherObj = forecastObject.getJSONArray(Forecast.WEATHER).getJSONObject(0);

        Weather weather = new Weather(
                weatherObj.getInt(Forecast.WEATHER_ID),
                weatherObj.getString(Forecast.WEATHER_MAIN),
                weatherObj.getString(Forecast.WEATHER_DESCRIPTION),
                weatherObj.getString(Forecast.WEATHER_ICON));

        JSONObject temperatureObj = forecastObject.getJSONObject(Forecast.TEMPERATURES);

        Forecast forecast = new Forecast();
        forecast.setDateMilliseconds(forecastObject.getLong(Forecast.DATE) * 1000);
        forecast.setPressure(forecastObject.getDouble(Forecast.PRESSURE));
        forecast.setWeather(weather);
        forecast.setHumidity(forecastObject.getInt(Forecast.HUMIDITY));
        forecast.setTemperatures(
                temperatureObj.getDouble(Forecast.TEMPERATURE_DAY),
                temperatureObj.getDouble(Forecast.TEMPERATURE_MIN),
                temperatureObj.getDouble(Forecast.TEMPERATURE_MAX),
                temperatureObj.getDouble(Forecast.TEMPERATURE_NIGHT),
                temperatureObj.getDouble(Forecast.TEMPERATURE_EVENING),
                temperatureObj.getDouble(Forecast.TEMPERATURE_MORNING)
        );

        return forecast;
    }
}
