package com.example.android.sunshine.app.models;

import java.util.ArrayList;

/**
 * Created by Jan on 03.01.2017.
 */

public class Forecast {

    public static final String DATE = "dt";

    public static final String WEATHER = "weather";
    public static final String WEATHER_ID = "id";
    public static final String WEATHER_MAIN = "main";
    public static final String WEATHER_DESCRIPTION = "description";
    public static final String WEATHER_ICON = "icon";

    public static final String HUMIDITY = "humidity";
    public static final String PRESSURE = "pressure";

    public static final String TEMPERATURES = "temp";
    public static final String TEMPERATURE_DAY = "day";
    public static final String TEMPERATURE_MIN = "min";
    public static final String TEMPERATURE_MAX = "max";
    public static final String TEMPERATURE_NIGHT = "night";
    public static final String TEMPERATURE_EVENING = "eve";
    public static final String TEMPERATURE_MORNING = "morn";

    private String date;
    private ArrayList<Integer> temperatures;
    private double pressure;
    private int humidity;
    private Weather weather;

    public Forecast() {
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public ArrayList<Integer> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(int tempDay, int tempMin, int tempMax, int tempNight, int tempEve, int tempMorn) {

        if (this.temperatures == null) {
            this.temperatures = new ArrayList<>();
        } else if (this.temperatures.size() > 0) {
            this.temperatures.clear();
        }

        temperatures.add(tempDay);
        temperatures.add(tempMin);
        temperatures.add(tempMax);
        temperatures.add(tempNight);
        temperatures.add(tempEve);
        temperatures.add(tempMorn);
    }

    public int getTemperature(String key) {

        int index;

        switch (key) {
            case TEMPERATURE_DAY:
                index = 0;
                break;
            case TEMPERATURE_MIN:
                index = 1;
                break;
            case TEMPERATURE_MAX:
                index = 2;
                break;
            case TEMPERATURE_NIGHT:
                index = 3;
                break;
            case TEMPERATURE_EVENING:
                index = 4;
                break;
            case TEMPERATURE_MORNING:
                index = 5;
                break;
            default:
                return -1000;
        }
        return this.temperatures.get(index);
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "date='" + date + '\'' +
                ", temperatures=" + temperatures +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", weather=" + weather +
                '}';
    }
}
