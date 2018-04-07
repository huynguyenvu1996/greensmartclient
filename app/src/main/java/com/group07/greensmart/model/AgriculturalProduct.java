package com.group07.greensmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nguyenvuhuy on 3/28/18.
 */

public class AgriculturalProduct {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("temp_min")
    @Expose
    private double minTemperature;

    @SerializedName("temp_max")
    @Expose
    private double maxTemperature;

    @SerializedName("humidity_min")
    @Expose
    private double minHumidity;

    @SerializedName("humidity_max")
    @Expose
    private double maxHumidity;

    @SerializedName("detect_rain")
    @Expose
    private boolean detectRain;

    @SerializedName("drying")
    @Expose
    private boolean drying;

    @SerializedName("notification")
    @Expose
    private boolean notification;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("_rev")
    @Expose
    private String rev;


    public AgriculturalProduct(String id, String name, String image, double minTemperature, double maxTemperature, double minHumidity, double maxHumidity, boolean detectRain, boolean drying, boolean notification, String createdAt, String rev) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
        this.detectRain = detectRain;
        this.drying = drying;
        this.notification = notification;
        this.createdAt = createdAt;
        this.rev = rev;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(double minHumidity) {
        this.minHumidity = minHumidity;
    }

    public double getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public boolean isDetectRain() {
        return detectRain;
    }

    public void setDetectRain(boolean detectRain) {
        this.detectRain = detectRain;
    }

    public boolean isDrying() {
        return drying;
    }

    public void setDrying(boolean drying) {
        this.drying = drying;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }
}
