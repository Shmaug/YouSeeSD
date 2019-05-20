package com.beep.youseesd.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.beep.youseesd.R;
import com.beep.youseesd.fragment.TourListFragment;
import com.beep.youseesd.util.WLog;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsTextView;

public class HomeActivity extends BaseActivity {

    private FloatingActionButton mCreateTourButton;
    private IconicsTextView weatherTextView;
    private BottomAppBar appBar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            // stack becomes empty means now we are back to the TourList.
            getAppBar().setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
            getAppBar().setFabAnimationMode(BottomAppBar.FAB_ANIMATION_MODE_SCALE);
            getWeatherTextView().setVisibility(View.VISIBLE);
            getFAB().setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add_24dp));
            mCreateTourButton.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), CreateTourActivity.class);
                startActivity(intent);
            });
            return;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupUI();

        TourListFragment tourListFragment = new TourListFragment();
        updateFragment(tourListFragment, null);

        WLog.i("home launched");
        WLog.i("check user session..");
        handleUserLogin();
    }

    private void handleUserLogin() {
//        AuthHandler.signinAnonymously(this, this);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        WLog.i(currentUser != null ? "uid: " + currentUser.getUid() : "currentUser is null");
        if (currentUser == null) {
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setupUI() {
        appBar = (BottomAppBar) findViewById(R.id.bottom_app_bar);
        weatherTextView = (IconicsTextView) findViewById(R.id.weather_text);
        weatherTextView.setDrawableStart(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_cloud).color(Color.WHITE).paddingDp(3).sizeDp(24));
        mCreateTourButton = (FloatingActionButton) findViewById(R.id.fab);
        mCreateTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateTourActivity.class);
                startActivity(intent);
            }
        });
    }

    public FloatingActionButton getFAB() {
        return mCreateTourButton;
    }

    public IconicsTextView getWeatherTextView() {
        return weatherTextView;
    }

    public BottomAppBar getAppBar() {
        return appBar;
    }
}
