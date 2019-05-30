package com.beep.youseesd.model;

import java.util.ArrayList;
import java.util.List;

public class Theme {
  //must be int type, ranging from 0 ~ (Tour.NUM_THEMES - 1)
  public int themeId;
  public String themeName;

  public Theme() {
    this.themeId = 0;
    this.themeName = null;
  }
}
