package com.beep.youseesd.model;

import java.util.ArrayList;

public class Tour {
    public int tourId; // could be String type
    public String title;
    public String subtitle;
    public String imageUrl;
    public double [] themeVector;
    public ArrayList<Location> locations;
    private static final int NUM_THEMES = 13;
    private static final int THEME_MAX_VALUE = 10;

    public Tour() {
        this.tourId = 0;
        this.title = null;
        this.subtitle = null;
        this.imageUrl = null;
        this.themeVector = new double[NUM_THEMES];
        this.locations = new ArrayList<>();
    }

    // this method will only be used if algorithm #1 is selected
    public double getDifference(ArrayList<Theme> userInputThemes) {
        double difference = 0.0;

        for(Theme t : userInputThemes) {
            difference += Math.abs(THEME_MAX_VALUE - this.themeVector[t.themeId]);
        }

        return difference;
    }
}

