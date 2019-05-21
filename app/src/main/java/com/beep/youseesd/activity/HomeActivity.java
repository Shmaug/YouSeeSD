package com.beep.youseesd.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.beep.youseesd.R;
import com.beep.youseesd.fragment.TourListFragment;
import com.beep.youseesd.handler.AuthHandler;
import com.beep.youseesd.util.WLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsTextView;

/**
 * The main screen of our app
 */
public class HomeActivity extends BaseActivity implements OnCompleteListener<AuthResult> {

    private FloatingActionButton mCreateTourButton;
    private IconicsTextView weatherTextView;
    private BottomAppBar appBar;
    private OpenWeatherMapHelper helper;

    /**
     * If fragment stack is 0, renders bottom components again
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            // Stack becomes empty means now we are back to the TourList
            // App bar
            getAppBar().setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
            getAppBar().setFabAnimationMode(BottomAppBar.FAB_ANIMATION_MODE_SCALE);

            // Weather
            getWeatherTextView().setVisibility(View.VISIBLE);
            getFAB().setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add_24dp));

            // The tour button
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

        // create weather object
        helper = new OpenWeatherMapHelper("4c3866391f6138c27bfb9d71a837631e");
        helper.setUnits(Units.IMPERIAL);
        helper.setLang(Lang.ENGLISH);

        setupUI();

        TourListFragment tourListFragment = new TourListFragment();
        updateFragment(tourListFragment, null);

        WLog.i("home launched");
        WLog.i("check user session..");

//        handleUserLogin();
    }

    private void handleUserLogin() {
        AuthHandler.signinAnonymously(this, this);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        WLog.i(currentUser != null ? "uid: " + currentUser.getUid() : "currentUser is null");
    }

    /**
     * Set up the UI of the screen (app bar, weather, create tour button)
     */
    private void setupUI() {
        appBar = (BottomAppBar) findViewById(R.id.bottom_app_bar);
        HomeActivity temp = this;

        // Weather information
        weatherTextView = (IconicsTextView) findViewById(R.id.weather_text);
        helper.getCurrentWeatherByZipCode("92093", new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currWeather) {
                //WLog.i(currWeather.getWeather());
                //WLog.i(currWeather.getWeather().get(0).getDescription());
                String weather = currWeather.getWeather().get(0).getMain();
                WLog.i(weather);

              FontAwesome.Icon weatherIcon;

                switch(weather) {
                  case "Clouds":
                    weatherIcon = FontAwesome.Icon.faw_cloud;
                    break;
                  case "Clear":
                    weatherIcon = FontAwesome.Icon.faw_sun;
                    break;
                    case "Snow":
                        weatherIcon = FontAwesome.Icon.faw_snowflake;
                        break;
                    case "Rain":
                        weatherIcon = FontAwesome.Icon.faw_umbrella;
                  // default case includes Mist, Smoke, Haze, Dust, Fog, Sand, Dust, Ash, Squall, Tornado
                  default:
                    weatherIcon = FontAwesome.Icon.faw_cloud;
                    break;
                }

                weatherTextView.setDrawableStart((new IconicsDrawable(temp).icon(weatherIcon).color(Color.WHITE).paddingDp(3).sizeDp(24)));
                //weatherTextView.setDrawableStart(new IconicsDrawable(temp).icon(FontAwesome.Icon.faw_shower).color(Color.WHITE).paddingDp(3).sizeDp(24));

                /*if (weather.equals("clear")) {
                    weatherTextView.setDrawableStart(new IconicsDrawable(temp).icon(FontAwesome.Icon.faw_cloud).color(Color.WHITE).paddingDp(3).sizeDp(24));
                } else if (weather.equals("light rain")){
                    weatherTextView.setDrawableStart(new IconicsDrawable(temp).icon(FontAwesome.Icon.faw_cloud).color(Color.BLACK).paddingDp(3).sizeDp(48));
                } else if (weather.equals("broken clouds")) {

                }*/
            }

            @Override
            public void onFailure(Throwable throwable) {
                return;
            }
        });
        //weatherTextView.setDrawableStart(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_cloud).color(Color.WHITE).paddingDp(3).sizeDp(24));

        // Sets up click listener for the button to create a tour
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

    /**
     * When the activity is done loading, connect the user to Firebase
     * @param task
     */
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        FirebaseUser newUser = task.getResult().getUser();
    }
}
