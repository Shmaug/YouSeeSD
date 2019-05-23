package com.beep.youseesd.model;

import java.util.ArrayList;

public class Tour {
    private String title;
    private String imageUrl;
    private String subtitle;
    // user to check if the location was visited during the tour
    private boolean visited;
    // used for determining which Tour will be chosen, most likely hardcoded
    private double[] tourTheme;
    private ArrayList<Location> locations;

    public Tour(String title, String imageUrl, String subtitle, double[] tourTheme) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.subtitle = subtitle;
        this.tourTheme = tourTheme;
        this.locations = new ArrayList<>();
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

    public void toggleVisited() {
        this.visited = !this.visited;
    }

    public double[] getTourTheme() {
        return tourTheme;
    }

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
