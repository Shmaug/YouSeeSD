package com.beep.youseesd.model;

import androidx.annotation.NonNull;

import com.beep.youseesd.util.DatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TourSet {

  // A Theme map that has a size of Tour.NUM_THEMES (=13 for now)
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
//  private List<Tour> allTours;

  // list of all pre-made tours converted to be used
  private static List<Tour> allTours;
  public static Map<String, Location> allLocations;

  public static void setUpTours() {
    allTours = new ArrayList<>();

    // get all premade tours from database
    DatabaseReference databaseTours = DatabaseUtil.getAllTours();

    databaseTours.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // add each tour onto our list
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
          String dsid = ds.getKey();
          Tour t = ds.getValue(Tour.class);
          t.tourId = dsid;
          allTours.add(t);
        }
        System.out.println("Finished setting up tours: " + allTours.size());
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  public static void setUpLocations() {
    allLocations = new HashMap<>();

    DatabaseReference databaseLocations = DatabaseUtil.getAllLocations();

    databaseLocations.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // add each tour onto our list
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
          String dsid = ds.getKey();
          Location l = ds.getValue(Location.class);
          l.locationId = dsid;
          allLocations.put(dsid, l);
        }
        System.out.println("Finished setting up locations : " + allLocations.size());
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  public TourSet() {
    //allTours = new ArrayList<>();
    // Create tour constructing methods below and populate allTours
    //allTours.add(createTour1());
  }

  private Tour createTour1() {
//    Tour t1 = new Tour();
//    t1.title = "This is tour1";
//    t1.subtitle = "this is the summary!";
//    t1.imageUrl = "https://ucpa.ucsd.edu/images/image_library/triton-fountain-at-price-center.jpg";
//
//    return t1;

    return allTours.get(0);
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
      System.out.print("vector: " + t.tourId);
      for (double d : t.themeVector) {
        System.out.print(d + ", ");
      }
      System.out.println();
      if (smallestDifference > t.getDifference(userInputVector)) {
        smallestDifference = t.getDifference(userInputVector);
        bestFitTour = t;
      }
    }

    return bestFitTour;
  }
}
