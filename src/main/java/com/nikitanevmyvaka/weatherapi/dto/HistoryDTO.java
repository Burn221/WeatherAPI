package com.nikitanevmyvaka.weatherapi.dto;

public class HistoryDTO {
    private String city;
    private double latitude;
    private double longitude;
    private String time;
    private double temperature;
    private long relativeHumidity;
    private double windspeed;

    public HistoryDTO(String city, double latitude, double longitude, String time, double temperature, long relativeHumidity, double windspeed) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.temperature = temperature;
        this.relativeHumidity = relativeHumidity;
        this.windspeed = windspeed;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getCity() {
        return city;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }

    public long getRelativeHumidity() {
        return relativeHumidity;
    }

    public double getWindspeed() {
        return windspeed;
    }
}
