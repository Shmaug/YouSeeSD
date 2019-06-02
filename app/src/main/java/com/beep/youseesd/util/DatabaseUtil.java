package com.beep.youseesd.util;

import com.beep.youseesd.model.Tour;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseUtil {

  public static final String TOURS = "tours";
  public static final String USERS = "users";
  public static final String TOUR_ID = "tourId";
  public static final String LOCATIONS = "locations";

  public static DatabaseReference getTourDatabase(String uid) {
    return FirebaseDatabase.getInstance().getReference()
        .child(USERS)
        .child(uid)
        .child(TOURS);
  }

  public static DatabaseReference getAllLocations() {
    return FirebaseDatabase.getInstance().getReference()
        .child(LOCATIONS);
  }

  public static DatabaseReference getAllTours() {
    return FirebaseDatabase.getInstance().getReference()
        .child(TOURS);
  }

  public static DatabaseReference getSingleTourDatabase(String uid, String tourId) {
    return getTourDatabase(uid).child(tourId);
  }

  public static void createTour(String uid, Tour t, DatabaseReference.CompletionListener listener) {
    DatabaseReference ref = getTourDatabase(uid);
    // generate new key (random ids)
    String tourId = ref.push().getKey();
    t.tourId = tourId;
    ref.child(tourId).setValue(t, listener);
  }

  public static void deleteTour(String uid, String tourId) {
    getSingleTourDatabase(uid, tourId).removeValue();
  }
}
