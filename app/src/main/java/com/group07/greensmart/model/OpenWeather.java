package com.group07.greensmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nguyenvuhuy on 4/11/18.
 */

public class OpenWeather implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("city_id")
    @Expose
    private double cityId;

    @SerializedName("city_name")
    @Expose
    private String cityName;

    @SerializedName("temperature")
    @Expose
    private double temperature;

    @SerializedName("humidity")
    @Expose
    private double humidity;

    @SerializedName("rain")
    @Expose
    private boolean rain;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("dt")
    @Expose
    private String dt;

    @SerializedName("_rev")
    @Expose
    private String rev;

    public OpenWeather(String id, double cityId, String cityName, double temperature, double humidity, boolean rain, String description, String icon, String dt, String rev) {
        this.id = id;
        this.cityId = cityId;
        this.cityName = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.rain = rain;
        this.description = description;
        this.icon = icon;
        this.dt = dt;
        this.rev = rev;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCityId() {
        return cityId;
    }

    public void setCityId(double cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public boolean isRain() {
        return rain;
    }

    public void setRain(boolean rain) {
        this.rain = rain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }
}
