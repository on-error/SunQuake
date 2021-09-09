package net.in.googol.quakereport;

public class Earthquake {
    private double magnitude;
    private String place;
    private long timeInMillis;
    private String url;

    public Earthquake(double magnitude, String place, long timeInMillis, String url) {
        this.magnitude = magnitude;
        this.place = place;
        this.timeInMillis = timeInMillis;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }
}
