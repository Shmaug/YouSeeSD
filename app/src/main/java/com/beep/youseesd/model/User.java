package com.beep.youseesd.model;

public class User {

    private static User singleton;

    public User() {

    }

    public static User getInstance() {
        if (singleton == null) {
            singleton = new User();
        }

        return singleton;
    }
}
