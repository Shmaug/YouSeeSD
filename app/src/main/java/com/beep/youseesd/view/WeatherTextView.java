package com.beep.youseesd.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.beep.youseesd.model.Weather;
import com.beep.youseesd.util.WLog;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsTextView;

/**
 * A weather text view with nice icon set.
 */
public class WeatherTextView extends IconicsTextView {

  /**
   * A constructor called when programmatically inflating the view.
   */
  public WeatherTextView(Context context) {
    super(context);
    setupUI();
  }

  /**
   * A constructor called when injecting the view into the xml layout.
   */
  public WeatherTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setupUI();
  }

  /**
   * A helper method to setup the UI components.
   */
  private void setupUI() {
    setTextColor(Color.WHITE);
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
  }

  /**
   * Displays with the given weather info.
   */
  public void updateWeatherText(Weather weather) {
    WLog.i("weather updated!");
    String displayUnit = weather.getUnit().equals("celcius") ? "°C" : "°F";
    setText(weather.getTemperature() + displayUnit);
    setDrawableStart(new IconicsDrawable(getContext())
        .icon(getWeatherIcon(weather.getWeather()))
        .color(Color.WHITE)
        .paddingDp(4)
        .sizeDp(24));
    setVisibility(View.VISIBLE);
  }

  /**
   * A helper method to decide which icon to display depending on the weather
   *
   * @param weather the current live weather.
   */
  private FontAwesome.Icon getWeatherIcon(String weather) {
    switch (weather) {
      case "Clouds":
        return FontAwesome.Icon.faw_cloud;
      case "Clear":
        return FontAwesome.Icon.faw_sun;
      case "Snow":
        return FontAwesome.Icon.faw_snowflake;
      case "Rain":
        return FontAwesome.Icon.faw_umbrella;
      // default case includes Mist, Smoke, Haze, Dust, Fog, Sand, Dust, Ash, Squall, Tornado
      default:
        return FontAwesome.Icon.faw_cloud;
    }
  }
}
