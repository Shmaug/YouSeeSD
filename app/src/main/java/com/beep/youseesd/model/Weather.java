package com.beep.youseesd.model;

public class Weather {

  private String mUnit;
  private String mWeather;
  private int mTemperature;

  public Weather(String unit, String weather, int temperature) {
    this.mUnit = unit;
    this.mWeather = weather;
    this.mTemperature = temperature;
  }

  public int getTemperature() {
    return mTemperature;
  }

  public String getUnit() {
    return mUnit;
  }

  public String getWeather() {
    return mWeather;
  }
}
