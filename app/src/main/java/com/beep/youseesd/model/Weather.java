package com.beep.youseesd.model;

/**
 * Holds the info about the weather
 */
public class Weather {

  private String mUnit;
  private String mWeather;
  private int mTemperature;

  /**
   * A constructor to get the current weather info
   */
  public Weather(String unit, String weather, int temperature) {
    this.mUnit = unit;
    this.mWeather = weather;
    this.mTemperature = temperature;
  }

  /**
   * A getter for temperature
   */
  public int getTemperature() {
    return mTemperature;
  }

  /**
   * A getter for unit
   */
  public String getUnit() {
    return mUnit;
  }

  /**
   * A getter for weather
   */
  public String getWeather() {
    return mWeather;
  }
}
