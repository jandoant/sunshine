package com.example.android.sunshine.app;

/**
 * Created by Jan on 03.01.2017.
 */

public class Forecast {

    private String description;
    private int temperatureDay;
    private int humidity;

    public Forecast(String description, int humidity, int temperatureDay) {
        this.description = description;
        this.humidity = humidity;
        this.temperatureDay = temperatureDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemperatureDay() {
        return temperatureDay;
    }

    public void setTemperatureDay(int temperatureDay) {
        this.temperatureDay = temperatureDay;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
