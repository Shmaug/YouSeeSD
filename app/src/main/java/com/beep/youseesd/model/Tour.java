package com.beep.youseesd.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles the information stored within a single Tour
 */
public class Tour {

  @Exclude
  private static final int THEME_MAX_VALUE = 10;

  private String tourId;
  private String title;
  private String subtitle;
  private String imageUrl;

  private ArrayList<Double> themeVector;
  private ArrayList<String> locations;
  private int estimatedTime;

  /**
   * No-arg constructor for our Tour object
   */
  public Tour() {
    this.setTourId("");
    this.setTitle(null);
    this.setSubtitle(null);
    this.setImageUrl(null);
    this.setEstimatedTime(0);
    this.setThemeVector(new ArrayList<>());
    this.setLocations(new ArrayList<>());
  }

  /**
   * Getter for THEME_MAX_VALUE constant
   *
   * @return THEME_MAX_VALUE
   */
  public static int getThemeMaxValue() {
    return THEME_MAX_VALUE;
  }

  /**
   * Algorithm to calculate the difference between the user's input themes and this tour's theme
   *
   * @param userInputThemes the themes the user selected
   * @return the difference between the two vectors
   */
  public double getDifference(List<Theme> userInputThemes) {
    double difference = 0.0;

    for (Theme t : userInputThemes) {
      difference += Math.abs(getThemeMaxValue() - this.getThemeVector().get(t.getThemeId()));
    }

    return difference;
  }

  /**
   * Getter for tourId
   *
   * @return tourId
   */
  public String getTourId() {
    return tourId;
  }

  /**
   * Setter for tourId
   *
   * @param tourId the tourId to be set
   */
  public void setTourId(String tourId) {
    this.tourId = tourId;
  }

  /**
   * Getter for title
   *
   * @return title
   */
  public String getTitle() {
    return title;
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
   * Getter for subtitle
   *
   * @return subtitle
   */
  public String getSubtitle() {
    return subtitle;
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
   * Getter for imageUrl
   *
   * @return imageUrl
   */
  public String getImageUrl() {
    return imageUrl;
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
   * Getter for themeVector
   *
   * @return themeVector
   */
  public ArrayList<Double> getThemeVector() {
    return themeVector;
  }

  /**
   * Setter for themeVector
   *
   * @param themeVector the themeVector to be set
   */
  public void setThemeVector(ArrayList<Double> themeVector) {
    this.themeVector = themeVector;
  }

  /**
   * Getter for locations
   *
   * @return locations
   */
  public ArrayList<String> getLocations() {
    return locations;
  }

  /**
   * Setter for locations
   *
   * @param locations the locations to be set
   */
  public void setLocations(ArrayList<String> locations) {
    this.locations = locations;
  }

  /**
   * Getter for estimatedTime
   *
   * @return estimatedTime
   */
  public int getEstimatedTime() {
    return estimatedTime;
  }

  /**
   * Setter for estimatedTime
   *
   * @param estimatedTime the estimatedTime to be set
   */
  public void setEstimatedTime(int estimatedTime) {
    this.estimatedTime = estimatedTime;
  }
}

