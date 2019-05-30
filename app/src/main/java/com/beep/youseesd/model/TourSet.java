package com.beep.youseesd.model;

import java.util.ArrayList;
import java.util.List;

public class TourSet {

  // an arraylist that stores all of the pre-made tours
  public List<Tour> setOfTours;

  // the arraylist of tours that the user wants to see (tours that were "generated" by the user)
  public List<Tour> userTours;

  public TourSet() {
    setOfTours = new ArrayList<>();
  }

  public void addTour(Tour bestFitTour) {
    userTours.add(bestFitTour);
  }
}
