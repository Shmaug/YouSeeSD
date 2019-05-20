package com.beep.youseesd.model;

public class Location {
    public String title;
    public String subtitle;
    public String imageUrl;

    public Location() {
        this.title = "";
        this.subtitle = "";
        this.imageUrl = "";
    }

    public Location(String title, String subtitle, String imageUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
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
}
