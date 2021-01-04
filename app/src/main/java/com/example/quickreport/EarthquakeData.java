package com.example.quickreport;

public class EarthquakeData {
    private final double magnitude;
    private final String city;
    private final String date;
    private final String time;
    private final String url;

    EarthquakeData(double magnitude, String city, String date, String time, String url) {
        this.magnitude = magnitude;
        this.city = city;
        this.date = date;
        this.time = time;
        this.url = url;
    }

    public double getMagnitude() {
        return this.magnitude;
    }

    public String getCity() {
        return this.city;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public String getUrl() {
        return this.url;
    }
}
