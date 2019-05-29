package com.beep.youseesd.model;

import java.util.ArrayList;

public class Theme {
    private String themeId;
    private String themeName;
    private ArrayList<Location> locations;
    private ArrayList<PathPoint> pathPoints;
    private ArrayList<ChipTag> chipTags;
    //private boolean includedInTour;

    public Theme(String themeId, String themeName) {
        this.themeId = themeId;
        this.themeName = themeName;
        //includedInTour = false;
        this.locations = new ArrayList<>();
        this.pathPoints = new ArrayList<>();
        this.chipTags = new ArrayList<>();
    }

    public String getThemeName() {
        return themeName;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public ArrayList<PathPoint> getPathPoints() {
        return pathPoints;
    }

    public ArrayList<ChipTag> getChipTags() {
        return chipTags;
    }

}
