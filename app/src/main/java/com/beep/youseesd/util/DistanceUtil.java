package com.beep.youseesd.util;

/**
 * Utility class to help convert from km to miles
 */
public class DistanceUtil {
  /**
   * Computes the distance in miles
   *
   * @param km the kilometers we want to convert
   * @return the distance in miles
   */
  public static double toMiles(double km) {
    return km * 0.621371;
  }
}
