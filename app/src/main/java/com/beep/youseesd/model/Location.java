package com.beep.youseesd.model;

public class Location {
    public String locationId;
    public String title;
    public String subtitle;
    public String imageUrl;

    public Location(String locationId, String title, String subtitle, String imageUrl) {
        this.locationId = locationId;
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
    }

    // getters
    public String getTitle() {
        return this.title;
    }
    public String getSubtitle() {
        return this.subtitle;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }

    // setters
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
