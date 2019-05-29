package com.beep.youseesd.model;

import java.util.ArrayList;

public class Tour {
    private String title;
    private String imageUrl;
    private String subtitle;
    // used for determining which Tour will be chosen, most likely hardcoded
    private ArrayList<Theme> themes;
    private ArrayList<Location> locations;
    private ArrayList<PathPoint> path;

    public Tour(String title, String imageUrl, String subtitle, ArrayList<Theme> themes, ArrayList<Location> locations, ArrayList<PathPoint> path) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.subtitle = subtitle;
        this.themes = themes == null ? new ArrayList<>() : themes;
        this.locations = locations == null ? new ArrayList<>() : locations;
        this.path = path == null ? new ArrayList<>() : path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArrayList<Theme> getThemeList() {
        return themes;
    }

    public ArrayList<Location> getLocationList() {
        return locations;
    }

    // this method will only be used if algorithm #1 is selected
    /*
    public double getVariance(double[] userInputVector) {
        double variance = 0.0;

        // We assume that the userInputVector will have the same size as theme
        for(int i = 0; i < tourTheme.length; i++) {
            double diff = tourTheme[i] - userInputVector[i];
            variance += (diff * diff);
        }

        return variance;
    }
    */
}
