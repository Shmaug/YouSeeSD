package com.beep.youseesd.model;

import java.util.ArrayList;
import java.util.Collections;

public class TourSet {
    // an arraylist that stores all of the pre-made tours
    private ArrayList<Tour> allTours;

    // the arraylist of tours that the user wants to see (tours that were "generated" by the user)
    //private ArrayList<Tour> userTours;

    public TourSet() {
        allTours = new ArrayList<>();
    }

    //public void addTour(Tour bestFitTour) { userTours.add(bestFitTour); }

    // algorithm #1: Use vectors of doubles to find the best fit
    public Tour findBestFitTour(ArrayList<Theme> userInputVector) {
        if(userInputVector == null) {return null;}

        // variable that has the smallest difference
        Tour bestFitTour = null;
        double smallestDifference = 999999999;

        for(Tour t : allTours) {
            if(smallestDifference > t.getDifference(userInputVector)) {
                smallestDifference = t.getDifference(userInputVector);
                bestFitTour = t;
            }
        }

        return bestFitTour;
    }

}
