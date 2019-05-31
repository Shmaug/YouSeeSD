package com.beep.youseesd.model;

import com.google.android.gms.maps.model.LatLng;
import java.util.Calendar;

public class Location {

  public String locationId; //could be String type
  public String title;
  public String subtitle;
  public String imageUrl;
  public double longitude;
  public double latitude;
  // Used for the locations' info.s during the tour
  public String description;
  public String seats;
  public String builtin;
  public String courses;
  public String tags;

  public Location() {
    this.locationId = "";
    this.title = null;
    this.subtitle = null;
    this.imageUrl = null;
    this.longitude = 0;
    this.latitude = 0;
    this.description = null;
    setUnvisited();
  }

  // only used in On-tour, not in sync with the firebase database.
  private long visitedTime;

  public boolean isVisited() {
    return visitedTime > 0;
  }

  public long getVisitedTime() {
    return visitedTime;
  }

  public LatLng generateLatLng() {
    return new LatLng(latitude, longitude);
  }

  // Returns the number of minutes passed since visiting the location
  public int calculateVisitedAgo() {
    return (int) ((Calendar.getInstance().getTimeInMillis() - visitedTime) / 60);
  }

  public void setVisited() {
    visitedTime = Calendar.getInstance().getTimeInMillis();
  }

  public void setUnvisited() {
    visitedTime = 0;
  }
}
