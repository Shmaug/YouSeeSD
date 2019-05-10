package com.beep.youseesd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.beep.youseesd.R;
import com.beep.youseesd.fragment.TourListFragment;
import com.beep.youseesd.handler.AuthHandler;
import com.beep.youseesd.util.WLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends BaseActivity implements OnCompleteListener<AuthResult> {

    private FloatingActionButton mCreateTourButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupUI();

        TourListFragment tourListFragment = new TourListFragment();
        updateFragment(tourListFragment);

        WLog.i("home launched");
        WLog.i("check user session..");
//        handleUserLogin();
    }

    private void handleUserLogin() {
        AuthHandler.signinAnonymously(this, this);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        WLog.i(currentUser != null ? "uid: " + currentUser.getUid() : "currentUser is null");
    }

    private void setupUI() {
        mCreateTourButton = (FloatingActionButton) findViewById(R.id.fab);
        mCreateTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OnTourActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        FirebaseUser newUser = task.getResult().getUser();
    }
}
