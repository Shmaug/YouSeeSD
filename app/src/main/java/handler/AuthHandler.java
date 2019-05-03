package handler;

import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthHandler {

    public static void signinAnonymously(Activity act, OnCompleteListener<AuthResult> listener) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return;
        }

        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(act, listener);
    }
}
