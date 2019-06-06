package com.beep.youseesd.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.beep.youseesd.R;
import com.beep.youseesd.application.App;
import com.beep.youseesd.fragment.TourListFragment;
import com.beep.youseesd.handler.WeatherHandler;
import com.beep.youseesd.model.TourSet;
import com.beep.youseesd.model.Weather;
import com.beep.youseesd.util.WLog;
import com.beep.youseesd.view.WeatherTextView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.iconics.view.IconicsTextView;

/**
 * The main screen of our app
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, WeatherHandler.WeatherCallback {

  private FloatingActionButton mCreateTourButton;
  private WeatherTextView weatherTextView;
  private BottomAppBar appBar;

  private WeatherHandler mWeatherHandler;

  /**
   * Handles the back button behavior If fragment stack is 0, renders bottom components again
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
      mCreateTourButton.setOnClickListener(this);
    }
  }

  /**
   * Initial setup of our application Sets up our tours and locations from database. Sets up API for
   * the weather units
   *
   * @param savedInstanceState used as a lifecycle method
   */
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    WLog.i("home launched");

    // retrieve tours and locations from database
    TourSet.setUpTours();
    TourSet.setUpLocations();

    // setup the UI and display the list of tours saved under the user's account
    setupUI();

    // log in the user
    handleUserLogin();
  }

  /**
   * Handles the user's login by tying in their device to an account on firebase
   */
  private void handleUserLogin() {
    WLog.i("check user session..");
    FirebaseUser currentUser = App.getUser();
    WLog.i(currentUser != null ? "uid: " + currentUser.getUid() : "currentUser is null");

    // load the "register device" screen if the user is not registered on firebase
    // otherwise, don't do anything and remain on this screen
    if (currentUser == null) {
      Intent intent = new Intent(this, IntroActivity.class);
      startActivity(intent);
      finish();
    } else {
      WLog.i("user not null: " + currentUser.getUid());
      // create weather object
      setupWeather();
    }
  }

  /**
   * Set up the UI of the screen (app bar, weather, create tour button)
   */
  private void setupUI() {
    appBar = findViewById(R.id.bottom_app_bar);
    weatherTextView = findViewById(R.id.weather_text);
    mCreateTourButton = findViewById(R.id.fab);
    weatherTextView.setOnClickListener(this);
    mCreateTourButton.setOnClickListener(this);

    updateFragment(new TourListFragment(), null);
  }

  /**
   * Set up the weather information in the bottom right hand corner of the screen
   */
  private void setupWeather() {
    if (mWeatherHandler == null) {
      SharedPreferences settings = getSharedPreferences("Temperature", 0);
      mWeatherHandler = new WeatherHandler(settings);
    }

    // get the current weather information
    mWeatherHandler.requestCurrentWeather(this);
  }

  /**
   * Getter for createTourButton
   *
   * @return a reference to the createTourButton
   */
  public FloatingActionButton getFAB() {
    return mCreateTourButton;
  }

  /**
   * Getter for the weatherTextView
   *
   * @return a reference to the weatherTextView
   */
  public IconicsTextView getWeatherTextView() {
    return weatherTextView;
  }

  /**
   * Getter for the appBar
   *
   * @return a reference to the appBar
   */
  public BottomAppBar getAppBar() {
    return appBar;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.weather_text:
        if (mWeatherHandler.flipUnits()) {
          // get updated weather information in new units
          setupWeather();
        }
        break;

      case R.id.fab:
        Intent intent = new Intent(v.getContext(), CreateTourActivity.class);
        startActivity(intent);
        break;
    }
  }

  /**
   * Displays the temperature and weather icon if the API call was successful
   *
   * @param weather holds info about the current weather
   */
  @Override
  public void onWeatherRequestCallback(Weather weather) {
    // decide which icon to load based on weather
    weatherTextView.updateWeatherText(weather);
  }
}
