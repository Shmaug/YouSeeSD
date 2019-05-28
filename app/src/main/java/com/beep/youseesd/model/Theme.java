package com.beep.youseesd.model;

// theme = tags = hashtags(#)
public class Theme {
  private boolean includedInTour;
  private double[] tagTheme;

  public Theme(double[] tagTheme) {
    this.tagTheme = tagTheme;
    includedInTour = false;
  }

  public void toggleInclusion() {
    this.includedInTour = !this.includedInTour;
  }

  public double[] getTagTheme() {
    return tagTheme;
  }
}
