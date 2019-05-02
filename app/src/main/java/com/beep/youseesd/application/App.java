package com.beep.youseesd.application;

import android.app.Application;

import com.beep.youseesd.util.WLog;
import com.google.firebase.auth.FirebaseAuth;

public class App extends Application {

    private static App singleton = null;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        WLog.i("Our app just started!");
        FirebaseAuth.getInstance().signOut();
    }

    public static App getInstance() {
        return singleton;
    }
}