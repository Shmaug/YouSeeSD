package com.beep.youseesd.model;

import java.util.ArrayList;

public class Tour {
    private long createdTime;
    private boolean isPausing;
    private String title;
    private String imageUrl;
    private String subtitle;
    // used for determining which Tour will be chosen, most likely hardcoded
    private ArrayList<Double> theme;

    public Tour() {

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

    public ArrayList<Double> getTheme() {
        return theme;
    }
}
