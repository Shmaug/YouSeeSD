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

  @Exclude
  public double[] themeVector;

  public ArrayList<String> locations;
  public int estimatedTime;

  public Tour() {
    this.tourId = "";
    this.title = null;
    this.subtitle = null;
    this.imageUrl = null;
    this.estimatedTime = 0;
    this.themeVector = new double[NUM_THEMES];
    this.locations = new ArrayList<>();
  }

  // Calculates the difference between the user's input themes and this tour's theme
  public double getDifference(List<Theme> userInputThemes) {
    double difference = 0.0;

    for (Theme t : userInputThemes) {
      difference += Math.abs(THEME_MAX_VALUE - this.themeVector[t.themeId]);
    }

    return difference;
  }
}

