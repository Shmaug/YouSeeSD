package com.beep.youseesd.model;

import com.google.android.gms.maps.model.LatLng;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Class that stores information about a single location on our tour
 */
public class Location {

  //could be String type
  private String locationId;
  private String title;
  private String subtitle;
  private String imageUrl;
  private double longitude;
  private double latitude;
  // Used for the locations' info.s during the tour
  private String description;
  private String seats;
  private String builtin;
  private String courses;
  private String tags;

  // only used in On-tour, not in sync with the firebase database.
  private long visitedTime;

  /**
   * No-arg constructor for our Location object
   */
  public Location() {
    this.setLocationId("");
    this.setTitle(null);
    this.setSubtitle(null);
    this.setImageUrl(null);
    this.setLongitude(0);
    this.setLatitude(0);
    this.setDescription(null);
    setUnvisited();
  }

  /**
   * Determines whether or not we have visited this location
   *
   * @return true if we have visited, false if not
   */
  public boolean isVisited() {
    return getVisitedTime() > 0;
  }

  /**
   * Getter for visitedTime
   *
   * @return visitedTime
   */
  public long getVisitedTime() {
    return visitedTime;
  }

  /**
   * Generates a LatLng object using the location's latitude and longitude
   *
   * @return a LatLng object using this location's latitude and longitude values
   */
  public LatLng generateLatLng() {
    return new LatLng(getLatitude(), getLongitude());
  }

  /**
   * Calculates how long it's been since we've visited the location
   *
   * @return the number of minutes passed since visiting the location
   */
  public int calculateVisitedAgo() {
    long diff = (Calendar.getInstance().getTimeInMillis() - visitedTime);
    return (int) TimeUnit.MILLISECONDS.toMinutes(diff);
  }

  /**
   * Setter wrapper for visitedTime using the current time
   */
  public void setVisited() {
    setVisitedTime(Calendar.getInstance().getTimeInMillis());
  }

  /**
   * Setter wrapper when we haven't visited the location
   */
  public void setUnvisited() {
    setVisitedTime(0);
  }

  /**
   * Getter for title
   *
   * @return title
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Getter for subtitle
   *
   * @return subtitle
   */
  public String getSubtitle() {
    return this.subtitle;
  }

  /**
   * Getter for description
   *
   * @return description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Getter for imageUrl
   *
   * @return imageUrl
   */
  public String getImageUrl() {
    return this.imageUrl;
  }

  /**
   * Getter for locationId
   *
   * @return locationId
   */
  public String getLocationId() {
    return locationId;
  }

  /**
   * Setter for locationId
   *
   * @param locationId the locationId to be set
   */
  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

  /**
   * Setter for title
   *
   * @param title the title to be set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Setter for subtitle
   *
   * @param subtitle the subtitle to be set
   */
  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  /**
   * Setter for imageUrl
   *
   * @param imageUrl the imageUrl to be set
   */
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  /**
   * Getter for longitude
   *
   * @return longitude
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Setter for longitude
   *
   * @param longitude the longitude to be set
   */
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  /**
   * Getter for latitude
   *
   * @return latitude
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Setter for latitude
   *
   * @param latitude the latitude to be set
   */
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  /**
   * Setter for description
   *
   * @param description the description to be set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Getter for seats
   *
   * @return seats
   */
  public String getSeats() {
    return seats;
  }

  /**
   * Setter for seats
   *
   * @param seats the seats to be set
   */
  public void setSeats(String seats) {
    this.seats = seats;
  }

  /**
   * Getter for builtin
   *
   * @return builtin
   */
  public String getBuiltin() {
    return builtin;
  }

  /**
   * Setter for builtin
   *
   * @param builtin the builtin to be set
   */
  public void setBuiltin(String builtin) {
    this.builtin = builtin;
  }

  /**
   * Getter for courses
   *
   * @return courses
   */
  public String getCourses() {
    return courses;
  }

  /**
   * Setter for courses
   *
   * @param courses the courses to be set
   */
  public void setCourses(String courses) {
    this.courses = courses;
  }

  /**
   * Getter for tags
   *
   * @return tags
   */
  public String getTags() {
    return tags;
  }

  /**
   * Setter for tags
   *
   * @param tags the tags to be set
   */
  public void setTags(String tags) {
    this.tags = tags;
  }

  /**
   * Setter for visitedTime
   *
   * @param visitedTime the visitedTime to be set
   */
  public void setVisitedTime(long visitedTime) {
    this.visitedTime = visitedTime;
  }
}
