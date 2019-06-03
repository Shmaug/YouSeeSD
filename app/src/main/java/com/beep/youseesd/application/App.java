package com.beep.youseesd.application;

import android.app.Application;
import com.beep.youseesd.util.WLog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class App extends Application {

  private static App singleton = null;

  @Override
  public void onCreate() {
    super.onCreate();
    singleton = this;
    WLog.i("Our app just started!");
  }

  public static App getInstance() {
    return singleton;
  }

  public static FirebaseUser getUser() {
    return FirebaseAuth.getInstance().getCurrentUser();
  }
}