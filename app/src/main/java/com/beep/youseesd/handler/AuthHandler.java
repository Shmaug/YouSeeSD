package com.beep.youseesd.handler;

import android.app.Activity;
import com.beep.youseesd.util.WLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Handles the authentication of a user in Firebase
 */
public class AuthHandler {

    /**
     * Attempt to sign in anonymously since we don't really store sensitive information
     *
     * @param act the activity we're performing the authentication upon
     * @param listener the listener to add to our firebase if logging in is successful
     */
    public static void signinAnonymously(Activity act, OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInAnonymously().addOnFailureListener(act,
            e -> WLog.i("user login failed"));
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(act, listener);
    }
}
