package com.beep.youseesd.model;

import java.util.ArrayList;
import java.util.List;

public class Theme {

  public String themeId;
  public String themeName;
  public List<Location> locations;
  public List<PathPoint> pathPoints;
  public List<ChipTag> chipTags;

  public Theme(String themeId, String themeName) {
    this();
    this.themeId = themeId;
    this.themeName = themeName;
  }

  public Theme() {
    this.locations = new ArrayList<>();
    this.pathPoints = new ArrayList<>();
    this.chipTags = new ArrayList<>();
  }
}
