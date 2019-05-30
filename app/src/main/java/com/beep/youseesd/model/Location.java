package com.beep.youseesd.model;

public class Location {
    private String locationId;
    private String title;
    private String subtitle;
    private String imageUrl;
    private double longitude;
    private double latitude;

    public Location(String locationId, String title, String subtitle, String imageUrl, double longitude, double latitude) {
        this.locationId = locationId;
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
        this.longitude = longitude;
        this.latitude = latitude;
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
    public double getLongitude() {
        return this.longitude;
    }
    public double getLatitude() {
        return this.latitude;
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
