package com.beep.youseesd.model;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;

/**
 * THIS CLASS IS NOW PRETTY MUCH POINTLESS
 */
public class Theme {
  //must be int type, ranging from 0 ~ (Tour.NUM_THEMES - 1)
  public int themeId;
  public String themeName;

  public Theme(String themeName, Integer themeId) {
    this.themeId = themeId;
    this.themeName = themeName;
  }

  @SuppressLint("DefaultLocale")
  @NonNull
  @Override
  public String toString() {
    return String.format("%s(%d)", themeName, themeId);
  }
}
