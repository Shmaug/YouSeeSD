package com.beep.youseesd.model;

import java.util.ArrayList;
import java.util.Collections;

// theme = tags (=hashtags?)
public class Theme {
    private double[] tagTheme;
    private boolean includedInTour;

    public Theme() {

    }

    public void toggleInclusion() {
        this.includedInTour = !this.includedInTour;
    }

}
