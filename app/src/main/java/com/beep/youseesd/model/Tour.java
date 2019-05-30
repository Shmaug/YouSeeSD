package com.beep.youseesd.model;

import java.util.List;

public class Tour {

  public String tourId;
  public String title;
  public String imageUrl;
  public String subtitle;
  public int estimatedTime;
  public List<Location> locations;
  public List<PathPoint> pathPoints;
  public List<Theme> themes;

}
