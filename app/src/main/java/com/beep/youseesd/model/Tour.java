package com.beep.youseesd.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Tour {

  @Exclude
  private static final int NUM_THEMES = 13;

  @Exclude
  private static final int THEME_MAX_VALUE = 10;

  public String tourId; // could be String type
  public String title;
  public String subtitle;
  public String imageUrl;

  //@Exclude
  public ArrayList<Double> themeVector;

  public ArrayList<String> locations;
  public int estimatedTime;
  public List<String> selectedTags;

  public Tour() {
    this.tourId = "";
    this.title = null;
    this.subtitle = null;
    this.imageUrl = null;
    this.estimatedTime = 0;
    this.themeVector = new ArrayList<>();
    this.locations = new ArrayList<>();
    this.selectedTags = new ArrayList<>();
  }

  // Calculates the difference between the user's input themes and this tour's theme
  public double getDifference(List<Theme> userInputThemes) {
    double difference = 0.0;

    for (Theme t : userInputThemes) {
      difference += Math.abs(THEME_MAX_VALUE - this.themeVector.get(t.themeId));
    }

    return difference;
  }
}

