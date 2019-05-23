package com.beep.youseesd.model;

import java.util.HashSet;

// theme = tags = hashtags(#)
public class Theme {
    private boolean includedInTour;
    // note that only one of these fields will be used depending on which algorithm
    private double[] tagTheme;
    private HashSet<Location> locations;

    public Theme(double [] tagTheme, HashSet<Location> locations) {
        this.tagTheme = tagTheme;
        this.locations = locations;
        includedInTour = false;
    }

    public void toggleInclusion() {
        this.includedInTour = !this.includedInTour;
    }

    public HashSet<Location> getLocations() {
        return locations;
    }

    public double[] getTagTheme() {
        return tagTheme;
    }

}
