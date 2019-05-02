package com.beep.youseesd.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.beep.youseesd.R;
import com.beep.youseesd.fragment.HomeFragment;
import com.beep.youseesd.util.WLog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import handler.AuthHandler;

public class IntroActivity extends BaseActivity {

    private Button mGoTourButton;
    private FirebaseAuth mAuth;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        WLog.i("intro launched");

        AuthHandler.signinAnonymously(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        WLog.i(currentUser != null ? currentUser.getUid() : "currentUser is null");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.title_home);
        mToolbar.setTitleTextColor(Color.WHITE);

        HomeFragment homeFragment = new HomeFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mainframe, homeFragment);
        transaction.commit();
    }
}
