package com.beep.youseesd.model;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;

/**
 * Class that wraps information around a Theme
 */
public class Theme {
  // must be int type, ranging from 0 ~ (Tour.NUM_THEMES - 1)
  private int themeId;
  private String themeName;

  /**
   * Constructor for a Theme
   *
   * @param themeName the name of the theme
   * @param themeId the id of the theme
   */
  public Theme(String themeName, Integer themeId) {
    this.setThemeId(themeId);
    this.setThemeName(themeName);
  }

  /**
   * String representation of a theme
   *
   * @return the string representation of a theme
   */
  @SuppressLint("DefaultLocale")
  @NonNull
  @Override
  public String toString() {
    return String.format("%s(%d)", getThemeName(), getThemeId());
  }

  /**
   * Getter for themeId
   *
   * @return themeId
   */
  public int getThemeId() {
    return themeId;
  }

  /**
   * Setter for themeId
   *
   * @param themeId the themeId to be set
   */
  public void setThemeId(int themeId) {
    this.themeId = themeId;
  }

  /**
   * Getter for themeName
   *
   * @return themeName
   */
  public String getThemeName() {
    return themeName;
  }

  /**
   * Setter for themeName
   *
   * @param themeName the themeName to be set
   */
  public void setThemeName(String themeName) {
    this.themeName = themeName;
  }
}
