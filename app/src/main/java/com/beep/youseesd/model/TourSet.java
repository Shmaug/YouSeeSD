package com.beep.youseesd.model;

import java.util.ArrayList;
import java.util.Collections;

public class TourSet {
    // an arraylist that stores all of the pre-made tours
    private ArrayList<Tour> setOfTours;
    // the arraylist of tours that the user wants to see (tours that were "generated" by the user)
    private ArrayList<Tour> userTours;

    public TourSet() {
        setOfTours = new ArrayList<>();
        // populate setOfTours with dummy data
        setOfTours.add(new Tour("Dummy Title 1", "dummy.imageUrl.1", "dummy subtitle 1", new double[] {0.5, 9.7}));
        setOfTours.add(new Tour("Dummy Title 2", "dummy.imageUrl.2", "dummy subtitle 2", new double[] {5.5, 3.3}));
        setOfTours.add(new Tour("Dummy Title 3", "dummy.imageUrl.3", "dummy subtitle 3", new double[] {6.2, 0.5}));
    }

    // algorithm #1: Use vectors of doubles to find the best fit
    public Tour findBestFitTour(ArrayList<Theme> userInputVector) {
        if(userInputVector == null) {return null;}

        // normalize the vectors that the user inputted into one vector
        double[] normalizedInputVector = new double[userInputVector.get(0).getTagTheme().length];
        for(int i = 0; i < userInputVector.size(); i++) {
            double sum = 0;
            for(Theme t : userInputVector) { sum += t.getTagTheme()[i]; }
            normalizedInputVector[i] = sum / userInputVector.size();
        }

        // list to store the variances
        ArrayList<Double> variances = new ArrayList<>();
        // store all the variances to an arraylist
        for(Tour t : setOfTours) { variances.add(t.getVariance(normalizedInputVector)); }
        // find minimal variance and its index
        int minIndex = variances.indexOf(Collections.min(variances));

        // return best fit tour
        //userTours.add(setOfTours.get(minIndex));
        return setOfTours.get(minIndex);
    }

    // algorithm #2:
    public Tour mergeLocationsGenerateTour(ArrayList<Theme> userInputThemes) {
        // dummy tour
        // note that for this algorithm the tourTheme field is unnecessary
        Tour t = new Tour("Dummy Title", "dummy.imageUrl.com", "dummy subtitle", null);

        // union all subsets of locations
        for(Theme theme : userInputThemes) {
            for(Location location : t.getLocations()) { t.addLocation(location); }
        }

        // return finalized tour
        //userTours.add(t);
        return t;
    }
}
