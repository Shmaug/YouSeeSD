package com.beep.youseesd.model;

import java.util.HashSet;
import android.os.Parcel;
import android.os.Parcelable;

public class Tour implements Parcelable {
    private long createdTime;
    private boolean isPausing;
    private String title;
    private String imageUrl;
    private String subtitle;
    private TourStop[] route;
    // used for determining which Tour will be chosen, most likely hardcoded
    private double[] tourTheme;
    private HashSet<Location> locations;
    // used to store the feedback results
    private int feedback;

    public Tour(String title, TourStop[] route) {
        setTitle(title);
        this.route = route;
    }
  
  
    protected Tour(Parcel in) {
        createdTime = in.readLong();
        isPausing = in.readByte() != 0;
        title = in.readString();
        imageUrl = in.readString();
        subtitle = in.readString();
        route = in.createTypedArray(TourStop.CREATOR);
    }

    public Tour(String title, String imageUrl, String subtitle, double[] tourTheme) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.subtitle = subtitle;
        this.tourTheme = tourTheme;
        this.locations = new HashSet<Location>();
    }
  
    // setters

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(createdTime);
        dest.writeByte((byte) (isPausing ? 1 : 0));
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(subtitle);
        dest.writeTypedArray(route, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tour> CREATOR = new Creator<Tour>() {
        @Override
        public Tour createFromParcel(Parcel in) {
            return new Tour(in);
        }

        @Override
        public Tour[] newArray(int size) {
            return new Tour[size];
        }
    };

    public Tour imageUrl(String url) {
        this.imageUrl = url;
        return this;
    }

    public Tour subTitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // getters

    public String getSubtitle() {
        return subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public double[] getTourTheme() {
        return tourTheme;
    }

    public HashSet<Location> getLocations() {
        return locations;
    }

    public void incrementLike(int feedback) {
        // feedback must be 1 or -1
        this.feedback += feedback;
    }

    // this method will only be used if algorithm #1 is selected
    public double getVariance(double[] userInputVector) {
        double variance = 0.0;

        // We assume that the userInputVector will have the same size as theme
        for(int i = 0; i < tourTheme.length; i++) {
            double diff = tourTheme[i] - userInputVector[i];
            variance += (diff * diff);
        }

        return variance;
    }

    public TourStop getStop(int index){ 
        return route[index];
    }
  
    public int getNumStops() {
        return route.length;
    }
}
