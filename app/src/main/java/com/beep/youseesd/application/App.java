package com.beep.youseesd.application;

import android.app.Application;
import com.beep.youseesd.util.WLog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Class that handles the creation of our app
 */
public class App extends Application {

  private static App singleton = null;

  /**
   * Lifecycle method that will only create one instance of our app using the singleton pattern
   */
  @Override
  public void onCreate() {
    super.onCreate();
    singleton = this;
    WLog.i("Our app just started!");
  }

  /**
   * Getter for our singleton if necessary
   *
   * @return our app singleton
   */
  public static App getInstance() {
    return singleton;
  }

  /**
   * Getter for the current user in firebase
   *
   * @return the current user as a FirebaseUser object
   */
  public static FirebaseUser getUser() {
    return FirebaseAuth.getInstance().getCurrentUser();
  }
}