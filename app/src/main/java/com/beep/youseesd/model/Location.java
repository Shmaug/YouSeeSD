package com.beep.youseesd.model;

public class Location {
    public String title;
    public String subtitle;
    public String imageUrl;
    // user to check if the location was visited during the tour
    private boolean visited;

    public Location() {
        this(null,null,null);
    }

    public Location(String title, String subtitle, String imageUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
        this.visited = false;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void toggleVisited() {
        this.visited = !this.visited;
    }

}
