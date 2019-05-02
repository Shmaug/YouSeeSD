package handler;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.beep.youseesd.util.WLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthHandler {

    public static void signinAnonymously(Activity act) {
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener(act, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            WLog.i("signInAnonymously:failure");
                        }

                        // ...
                    }
                });
    }
}
