package com.beep.youseesd.model;

import java.util.ArrayList;
import java.util.Collections;

public class TourSet {
    private ArrayList<Tour> setOfTours;

    public TourSet() {
        setOfTours = new ArrayList<>();
        // populate setOfTours with dummy data
        setOfTours.add(new Tour("Dummy Title 1", "dummy imageUrl 1", "dummy subtitle 1", new double[] {0.5, 2.7}));
        setOfTours.add(new Tour("Dummy Title 2", "dummy imageUrl 2", "dummy subtitle 2", new double[] {5.5, 3.3}));
    }

    public Tour findBestFitTour(double[] userInputVector) {
        // list to store the variances
        ArrayList<Double> variances = new ArrayList<>();

        // store all the variances to an arraylist
        for(Tour t : setOfTours) {
            variances.add(t.getVariance(userInputVector));
        }

        // find minimal variance and its index
        int minIndex = variances.indexOf(Collections.min(variances));

        // return best fit tour
        return setOfTours.get(minIndex);
    }
}
