package com.beep.youseesd.handler;

import android.content.SharedPreferences;
import com.beep.youseesd.model.Weather;
import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

/**
 * A handler for the weather feature: loading the current weather, and ability to toggle the
 * preferred display units between Fahrenheit and Celcius.
 */
public class WeatherHandler {

  /**
   * A callback method when the current weather is successfully loaded.
   */
  public interface WeatherCallback {
    void onWeatherRequestCallback(Weather weather);
  }

  public static final String FAHRENHEIT = "fahrenheit";
  public static final String CELCIUS = "celcius";

  private static final String UNIT_CELCIUS = Units.METRIC;
  private static final String UNIT_FAHRENHEIT = Units.IMPERIAL;

  private static final String API_KEY = "4c3866391f6138c27bfb9d71a837631e";
  private static final String UNITS = "units";

  private OpenWeatherMapHelper mHelper;
  private SharedPreferences mPreferenceSettings;

  /**
   * Sets up by first initializing the preferred units.
   */
  public WeatherHandler(SharedPreferences sharedPreferences) {
    mPreferenceSettings = sharedPreferences;
    mHelper = new OpenWeatherMapHelper(API_KEY);
    mHelper.setLang(Lang.ENGLISH);
    setupInitialUnits();
  }

  /**
   * A helper method to setup initial preferences.
   */
  private void setupInitialUnits() {
    boolean isCelcius = getCurrentPreferredUnits(mPreferenceSettings).equals(CELCIUS);
    mHelper.setUnits(isCelcius ? UNIT_CELCIUS : UNIT_FAHRENHEIT);
  }

  /**
   * Returns the currently selected units.
   *
   * @param pref A user preference settings
   */
  public String getCurrentPreferredUnits(SharedPreferences pref) {
    return pref.getString(UNITS, "");
  }

  /**
   * Flips the units from F to C or vice versa.
   *
   * @return lets the caller know if if was successful to update.
   */
  public boolean flipUnits() {
    SharedPreferences.Editor editor = mPreferenceSettings.edit();

    // flip units
    boolean wasCelcius = getCurrentPreferredUnits(mPreferenceSettings).equals(CELCIUS);
    mHelper.setUnits(wasCelcius ? UNIT_FAHRENHEIT : UNIT_CELCIUS);
    editor.putString(UNITS, wasCelcius ? FAHRENHEIT : CELCIUS);
    return editor.commit();
  }

  /**
   * Requests the server to load a new weather info.
   *
   * @param callback a callback function when the weather is successfully loaded.
   */
  public void requestCurrentWeather(WeatherCallback callback) {
    boolean isCelcius = getCurrentPreferredUnits(mPreferenceSettings).equals(CELCIUS);
    CurrentWeatherListener listener = new CurrentWeatherListener(callback, mHelper, isCelcius ? CELCIUS : FAHRENHEIT);
    listener.request();
  }

  /**
   * A helper class to load the current weather and callbacks to the caller.
   */
  private static class CurrentWeatherListener implements CurrentWeatherCallback {

    private static final String ZIPCODE = "92093";

    private final WeatherCallback mCallback;
    private final OpenWeatherMapHelper mHelper;
    private final String mCurrentUnit;


    /**
     * A constructor that gets all necessary instances to request the new weather.
     */
    CurrentWeatherListener(WeatherCallback callback, OpenWeatherMapHelper helper, String unit) {
      mCallback = callback;
      mHelper = helper;
      mCurrentUnit = unit;
    }

    /**
     * Loads the new weather from the server.
     */
    private void request() {
      mHelper.getCurrentWeatherByZipCode(ZIPCODE, this);
    }

    /**
     * Notifies with the new weather info.
     */
    @Override
    public void onSuccess(CurrentWeather currentWeather) {
      int temperature = (int) currentWeather.getMain().getTemp();
      String weather = currentWeather.getWeather().get(0).getMain();
      mCallback.onWeatherRequestCallback(new Weather(mCurrentUnit, weather, temperature));
    }

    /**
     * Fails to load the new weather, and we do nothing.
     */
    @Override
    public void onFailure(Throwable throwable) {

    }
  }
}
