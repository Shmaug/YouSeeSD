package com.beep.youseesd.util;

import com.beep.youseesd.model.Tour;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Utility class to streamline database access
 */
public class DatabaseUtil {

  private static final String TOURS = "tours";
  private static final String USERS = "users";
  private static final String TOUR_ID = "tourId";
  private static final String LOCATIONS = "locations";

  /**
   * Gets the tour database of a user
   *
   * @param uid the id of a user
   * @return the user's tour database
   */
  public static DatabaseReference getTourDatabase(String uid) {
    return FirebaseDatabase.getInstance().getReference()
        .child(getUSERS())
        .child(uid)
        .child(getTOURS());
  }

  /**
   * Gets all of the available locations
   *
   * @return the locations database
   */
  public static DatabaseReference getAllLocations() {
    return FirebaseDatabase.getInstance().getReference()
        .child(getLOCATIONS());
  }

  /**
   * Gets all of the available tours
   *
   * @return the tours database
   */
  public static DatabaseReference getAllTours() {
    return FirebaseDatabase.getInstance().getReference()
        .child(getTOURS());
  }

  /**
   * Gets a single tour of a user
   *
   * @param uid the user's id
   * @param tourId the tour stored in a user's account
   * @return the database of the single tour of that user
   */
  public static DatabaseReference getSingleTourDatabase(String uid, String tourId) {
    return getTourDatabase(uid).child(tourId);
  }

  /**
   * Creates a new tour under a user's account
   *
   * @param uid the user's id
   * @param t the tour to be serialized
   * @param listener the listener we attach to await for completion
   */
  public static void createTour(String uid, Tour t, DatabaseReference.CompletionListener listener) {
    DatabaseReference ref = getTourDatabase(uid);

    // generate new key (random ids)
    String tourId = ref.push().getKey();
    t.setTourId(tourId);
    ref.child(tourId).setValue(t, listener);
  }

  /**
   * Delete a single tour under a user's account
   *
   * @param uid the user's id
   * @param tourId the tourId of the tour we want to delete
   */
  public static void deleteTour(String uid, String tourId) {
    getSingleTourDatabase(uid, tourId).removeValue();
  }

  /**
   * Getter for TOURS
   *
   * @return TOURS
   */
  public static String getTOURS() {
    return TOURS;
  }

  /**
   * Getter for USERS
   *
   * @return USERS
   */
  public static String getUSERS() {
    return USERS;
  }

  /**
   * Getter for TOUR_ID
   *
   * @return TOUR_ID
   */
  public static String getTourId() {
    return TOUR_ID;
  }

  /**
   * Getter for LOCATIONS
   *
   * @return LOCATIONS
   */
  public static String getLOCATIONS() {
    return LOCATIONS;
  }
}
