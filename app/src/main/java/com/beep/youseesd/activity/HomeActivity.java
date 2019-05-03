package com.beep.youseesd.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.beep.youseesd.R;
import com.beep.youseesd.fragment.HomeFragment;
import com.beep.youseesd.util.WLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import handler.AuthHandler;

public class HomeActivity extends BaseActivity implements OnCompleteListener<AuthResult> {

    private Toolbar mToolbar;
    private FloatingActionButton mCreateTourButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupUI();

        HomeFragment homeFragment = new HomeFragment();
        updateFragment(homeFragment);

        WLog.i("home launched");
        WLog.i("check user session..");
        AuthHandler.signinAnonymously(this, this);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        WLog.i(currentUser != null ? "uid: " + currentUser.getUid() : "currentUser is null");
    }

    private void setupUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.title_home);
        mToolbar.setTitleTextColor(Color.WHITE);

        mCreateTourButton = (FloatingActionButton) findViewById(R.id.btn_create_tour);
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
        FirebaseUser user = task.getResult().getUser();
    }
}
