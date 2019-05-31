package com.beep.youseesd.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TourSet {

  public static final Map<String, Integer> THEME_MAP;

  public static final String TAG_WARREN = "warren";
  public static final String TAG_ERC = "erc";
  public static final String TAG_SIXTH = "sixth";
  public static final String TAG_MARSHAL = "marshall";
  public static final String TAG_MUIR = "muir";
  public static final String TAG_REVELLE = "revelle";
  public static final String TAG_FITNESS = "fitness";
  public static final String TAG_SOCIAL = "social";
  public static final String TAG_ENGINEERING = "engineering";
  public static final String TAG_FOOD_LIVING = "food_living";
  public static final String TAG_POPULAR = "popular";
  public static final String TAG_NATURAL_SCIENCES = "natural_sciences";
  public static final String TAG_HUMANITIES = "humanities";

  static {
    THEME_MAP = new HashMap<>();
    THEME_MAP.put(TAG_WARREN, 0);
    THEME_MAP.put(TAG_ERC, 1);
    THEME_MAP.put(TAG_SIXTH, 2);
    THEME_MAP.put(TAG_MARSHAL, 3);
    THEME_MAP.put(TAG_MUIR, 4);
    THEME_MAP.put(TAG_REVELLE, 5);
    THEME_MAP.put(TAG_FITNESS, 6);
    THEME_MAP.put(TAG_SOCIAL, 7);
    THEME_MAP.put(TAG_ENGINEERING, 8);
    THEME_MAP.put(TAG_FOOD_LIVING, 9);
    THEME_MAP.put(TAG_POPULAR, 10);
    THEME_MAP.put(TAG_NATURAL_SCIENCES, 11);
    THEME_MAP.put(TAG_HUMANITIES, 12);
  }

  // the pre-made tours
  private List<Tour> allTours;

  public TourSet() {
    allTours = new ArrayList<>();
    allTours.add(createTour1());
  }

  private Tour createTour1() {
    Tour t = new Tour();
    t.title = "This is tour1";
    t.subtitle = "this is the summary!";
    t.imageUrl = "https://ucpa.ucsd.edu/images/image_library/triton-fountain-at-price-center.jpg";
    return t;
  }

  // algorithm #1: Use vectors of doubles to find the best fit
  public Tour findBestFitTour(List<Theme> userInputVector) {
    if (userInputVector == null || userInputVector.isEmpty()) {
      return null;
    }

    // variable that has the smallest difference
    Tour bestFitTour = null;
    double smallestDifference = Double.MAX_VALUE;
    for (Tour t : allTours) {
      if (smallestDifference > t.getDifference(userInputVector)) {
        smallestDifference = t.getDifference(userInputVector);
        bestFitTour = t;
      }
    }

    return bestFitTour;
  }
}
