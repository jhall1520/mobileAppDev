package com.example.group14_inclass08;

/**
 * InClass08
 * Weather.java
 * Joel Hall
 * Jimmy Kropp
 */
public class Weather {

    String city;
    String country;
    String description;
    String icon;
    String temp;
    String tempMax;
    String tempMin;
    String windSpeed;
    String windDegree;
    String cloudiness;
    String humidity;

    public Weather(String city, String country, String description, String icon, String temp, String tempMax, String tempMin, String windSpeed, String windDegree, String cloudiness, String humidity) {
        this.city = city;
        this.country = country;
        this.description = description;
        this.icon = icon;
        this.temp = temp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.cloudiness = cloudiness;
        this.humidity = humidity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(String windDegree) {
        this.windDegree = windDegree;
    }

    public String getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(String cloudiness) {
        this.cloudiness = cloudiness;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
