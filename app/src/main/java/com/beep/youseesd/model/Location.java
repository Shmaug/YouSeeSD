package com.beep.youseesd.model;

import com.google.android.gms.maps.model.LatLng;
import java.util.Calendar;

public class Location {

  public String locationId; //could be String type
  public String title;
  public String subtitle;
  public String imageUrl;
  public String description;
  public double longitude;
  public double latitude;
  public String seats;
  public String builtin;
  public String courses;
  public String tags;

  public Location() {
    this.locationId = "";
    this.title = null;
    this.subtitle = null;
    this.imageUrl = null;
    this.description = null;
    this.longitude = 0;
    this.latitude = 0;
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
