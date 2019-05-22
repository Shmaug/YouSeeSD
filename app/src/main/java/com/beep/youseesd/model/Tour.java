package com.beep.youseesd.model;

public class Tour {
    //private long createdTime;
    private boolean isPausing;
    private String title;
    private String imageUrl;
    private String subtitle;
    // used for determining which Tour will be chosen, most likely hardcoded
    private double[] theme;

    public Tour(String title, String imageUrl, String subtitle, double[] theme) {
        this.isPausing = false;
        this.title = title;
        this.imageUrl = imageUrl;
        this.subtitle = subtitle;
        this.theme = theme;
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

    public void togglePause() {
        this.isPausing = !this.isPausing;
    }

    public double[] getTheme() {
        return theme;
    }

    public double getVariance(double[] userInputVector) {
        double variance = 0.0;

        // We assume that the userInputVector will have the same size as theme
        for(int i = 0; i < theme.length; i++) {
            double diff = theme[i] - userInputVector[i];
            variance += (diff * diff);
        }

        return variance;
    }
}
