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

/**
 * Class that handles the creation of a new Tour and stores Theme information for said Tour
 */
public class TourSet {

  // a Theme map that has a size of Tour.NUM_THEMES
  private static final Map<String, Integer> THEME_MAP;

  private static final String TAG_WARREN = "warren";
  private static final String TAG_ERC = "erc";
  private static final String TAG_SIXTH = "sixth";
  private static final String TAG_MARSHALL = "marshall";
  private static final String TAG_MUIR = "muir";
  private static final String TAG_REVELLE = "revelle";
  private static final String TAG_FITNESS = "fitness";
  private static final String TAG_SOCIAL = "social";
  private static final String TAG_ENGINEERING = "engineering";
  private static final String TAG_FOOD_LIVING = "food_living";
  private static final String TAG_POPULAR = "popular";
  private static final String TAG_NATURAL_SCIENCES = "natural_sciences";
  private static final String TAG_HUMANITIES = "humanities";

  static {
    THEME_MAP = new HashMap<>();
    getThemeMap().put(getTagWarren(), 0);
    getThemeMap().put(getTagErc(), 1);
    getThemeMap().put(getTagSixth(), 2);
    getThemeMap().put(getTagMarshall(), 3);
    getThemeMap().put(getTagMuir(), 4);
    getThemeMap().put(getTagRevelle(), 5);
    getThemeMap().put(getTagFitness(), 6);
    getThemeMap().put(getTagSocial(), 7);
    getThemeMap().put(getTagEngineering(), 8);
    getThemeMap().put(getTagFoodLiving(), 9);
    getThemeMap().put(getTagPopular(), 10);
    getThemeMap().put(getTagNaturalSciences(), 11);
    getThemeMap().put(getTagHumanities(), 12);
  }

  // list of all pre-made tours converted to be used
  private static List<Tour> allTours;
  private static Map<String, Location> allLocations;

  /**
   * Method that sets up the tours list by pulling information from database
   */
  public static void setUpTours() {
    setAllTours(new ArrayList<>());

    // get all tours from database
    DatabaseReference databaseTours = DatabaseUtil.getAllTours();

    databaseTours.addListenerForSingleValueEvent(new ValueEventListener() {
      /**
       * Method that parses each tour and stores it into our list
       *
       * @param dataSnapshot contains all of the information regarding our tours
       */
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // add each tour into our list
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
          String dsid = ds.getKey();
          Tour t = ds.getValue(Tour.class);
          t.setTourId(dsid);
          getAllTours().add(t);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  /**
   * Method that sets up the locations list by pulling information from database
   */
  public static void setUpLocations() {
    setAllLocations(new HashMap<>());

    // get all locations from database
    DatabaseReference databaseLocations = DatabaseUtil.getAllLocations();

    databaseLocations.addListenerForSingleValueEvent(new ValueEventListener() {
      /**
       * Method that parses each location and stores it into our list
       *
       * @param dataSnapshot contains all of the information regarding our locations
       */
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // add each location into our list
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
          String dsid = ds.getKey();
          Location l = ds.getValue(Location.class);
          l.setLocationId(dsid);
          getAllLocations().put(dsid, l);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  /**
   * Getter for THEME_MAP
   *
   * @return THEME_MAP
   */
  public static Map<String, Integer> getThemeMap() {
    return THEME_MAP;
  }

  /**
   * Getter for TAG_WARREN
   *
   * @return TAG_WARREN
   */
  public static String getTagWarren() {
    return TAG_WARREN;
  }

  /**
   * Getter for TAG_ERC
   *
   * @return TAG_ERC
   */
  public static String getTagErc() {
    return TAG_ERC;
  }

  /**
   * Getter for TAG_SIXTH
   *
   * @return TAG_SIXTH
   */
  public static String getTagSixth() {
    return TAG_SIXTH;
  }

  /**
   * Getter for TAG_MARSHALL
   *
   * @return TAG_MARSHALL
   */
  public static String getTagMarshall() {
    return TAG_MARSHALL;
  }

  /**
   * Getter for TAG_MUIR
   *
   * @return TAG_MUIR
   */
  public static String getTagMuir() {
    return TAG_MUIR;
  }

  /**
   * Getter for TAG_REVELLE
   *
   * @return TAG_REVELLE
   */
  public static String getTagRevelle() {
    return TAG_REVELLE;
  }

  /**
   * Getter for TAG_FITNESS
   *
   * @return TAG_FITNESS
   */
  public static String getTagFitness() {
    return TAG_FITNESS;
  }

  /**
   * Getter for TAG_SOCIAL
   *
   * @return TAG_SOCIAL
   */
  public static String getTagSocial() {
    return TAG_SOCIAL;
  }

  /**
   * Getter for TAG_ENGINEERING
   *
   * @return TAG_ENGINEERING
   */
  public static String getTagEngineering() {
    return TAG_ENGINEERING;
  }

  /**
   * Getter for TAG_FOOD_LIVING
   *
   * @return TAG_FOOD_LIVING
   */
  public static String getTagFoodLiving() {
    return TAG_FOOD_LIVING;
  }

  /**
   * Getter for TAG_POPULAR
   *
   * @return TAG_POPULAR
   */
  public static String getTagPopular() {
    return TAG_POPULAR;
  }

  /**
   * Getter for TAG_NATURAL_SCIENCES
   *
   * @return TAG_NATURAL_SCIENCES
   */
  public static String getTagNaturalSciences() {
    return TAG_NATURAL_SCIENCES;
  }

  /**
   * Getter for TAG_HUMANITIES
   *
   * @return TAG_HUMANITIES
   */
  public static String getTagHumanities() {
    return TAG_HUMANITIES;
  }

  /**
   * Getter for allTours
   *
   * @return allTours
   */
  public static List<Tour> getAllTours() {
    return allTours;
  }

  /**
   * Setter for allTours
   *
   * @param allTours the allTours to be set
   */
  public static void setAllTours(List<Tour> allTours) {
    TourSet.allTours = allTours;
  }

  /**
   * Getter for allLocations
   *
   * @return allLocations
   */
  public static Map<String, Location> getAllLocations() {
    return allLocations;
  }

  /**
   * Setter for allLocations
   *
   * @param allLocations the allLocations to be set
   */
  public static void setAllLocations(Map<String, Location> allLocations) {
    TourSet.allLocations = allLocations;
  }

  /**
   * Method that uses vectors of doubles to find the tour of best fit
   *
   * @param userInputVector the themes the user selected
   * @return the Tour that has the best fit
   */
  public Tour findBestFitTour(List<Theme> userInputVector) {
    if (userInputVector == null || userInputVector.isEmpty()) {
      return null;
    }

    // variable that has the smallest difference
    Tour bestFitTour = null;
    double smallestDifference = Double.MAX_VALUE;

    // iterate through all available tours and pick the best one
    for (Tour t : getAllTours()) {
      if (smallestDifference > t.getDifference(userInputVector)) {
        smallestDifference = t.getDifference(userInputVector);
        bestFitTour = t;
      }
    }

    return bestFitTour;
  }
}
