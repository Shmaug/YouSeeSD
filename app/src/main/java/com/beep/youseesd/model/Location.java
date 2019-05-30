package com.beep.youseesd.model;

public class Location {
    public int locationId; //could be String type
    public String title;
    public String subtitle;
    public String imageUrl;
    public String description;
    public double longitude;
    public double latitude;

    public Location() {
        this.locationId = 0;
        this.title = null;
        this.subtitle = null;
        this.imageUrl = null;
        this.description = null;
        this.longitude = 0;
        this.latitude = 0;
    }


}
