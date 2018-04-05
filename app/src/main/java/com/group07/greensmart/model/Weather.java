package com.group07.greensmart.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nguyenvuhuy on 3/28/18.
 */

public class Weather {
    private static final String TEMPERATURE = "temperature";
    private static final String HUMIDITY = "humidity";
    private double temperature;
    private double humidity;

    public Weather(double temperature, double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public Weather(JSONObject data) throws JSONException {
        this.temperature = data.getDouble(TEMPERATURE);
        this.humidity = data.getDouble(HUMIDITY);
    }

    public Weather() {
        this.temperature = 0;
        this.humidity = 0;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setWeather(JSONObject data) throws JSONException {
        this.temperature = data.getDouble(TEMPERATURE);
        this.humidity = data.getDouble(HUMIDITY);
    }
}
