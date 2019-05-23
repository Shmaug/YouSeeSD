package com.beep.youseesd.model;

import java.util.HashSet;

public class Tour {
    private String title;
    private String imageUrl;
    private String subtitle;
    // used for determining which Tour will be chosen, most likely hardcoded
    private double[] tourTheme;
    private HashSet<Location> locations;

    public Tour(String title, String imageUrl, String subtitle, double[] tourTheme) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.subtitle = subtitle;
        this.tourTheme = tourTheme;
        this.locations = new HashSet<Location>();
    }

    public Tour imageUrl(String url) {
        this.imageUrl = url;
        return this;
    }

    public Tour subTitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Tour(String title) {
        setTitle(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public double[] getTourTheme() {
        return tourTheme;
    }

    public HashSet<Location> getLocations() {
        return locations;
    }

    // this method will only be used if algorithm #2 is selected
    public void addLocation(Location l) {
        locations.add(l);
    }

    // this method will only be used if algorithm #1 is selected
    public double getVariance(double[] userInputVector) {
        double variance = 0.0;

        // We assume that the userInputVector will have the same size as theme
        for(int i = 0; i < tourTheme.length; i++) {
            double diff = tourTheme[i] - userInputVector[i];
            variance += (diff * diff);
        }

        return variance;
    }
}
