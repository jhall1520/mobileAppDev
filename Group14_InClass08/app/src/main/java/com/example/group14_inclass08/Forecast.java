package com.example.group14_inclass08;

/**
 * InClass08
 * Forecast.java
 * Joel Hall
 * Jimmy Kropp
 */
public class Forecast {

    String date;
    String temp;
    String maxTemp;
    String minTemp;
    String icon;
    String humidity;
    String description;

    public Forecast(String date, String temp, String maxTemp, String minTemp, String icon, String humidity, String description) {
        this.date = date;
        this.temp = temp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.icon = icon;
        this.humidity = humidity;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
