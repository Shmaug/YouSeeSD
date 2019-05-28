package com.beep.youseesd.handler;

import android.app.Activity;
import androidx.annotation.NonNull;
import com.beep.youseesd.util.WLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthHandler {

    public static void signinAnonymously(Activity act, OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInAnonymously().addOnFailureListener(act, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                WLog.i("user login failed");
            }
        });

        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(act, listener);
    }
}
