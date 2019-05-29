package com.beep.youseesd.model;

import java.util.ArrayList;

public class PathPoint {
    private double latitude;
    private double longitude;
    private ArrayList<PathPoint> neighbors;
    private static final double walkSpeed = 1.5; // value is temporary and must be changed

    public PathPoint() {
        latitude = 0.0;
        longitude = 0.0;
        neighbors = new ArrayList<>();
    }

    public PathPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        neighbors = new ArrayList<>();
    }

    // setters
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setNeighbors(ArrayList<PathPoint> neighbors) {
        this.neighbors = neighbors;
    }
    public void addNeighbor(PathPoint neighbor) {
        this.neighbors.add(neighbor);
    }

    // getters
    public double getLatitude() {
        return this.latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public ArrayList<PathPoint> getNeighbors() {
        return this.neighbors;
    }

    public double getDistance(PathPoint from) {
        double latDiff = from.getLatitude() - this.getLatitude();
        double longDiff = from.getLongitude() - this.getLongitude();
        return Math.sqrt(latDiff * latDiff + longDiff * longDiff);
    }

    public int getTravelTime(PathPoint from) {
        // returns the estimated travel time in minutes
        double distance = this.getDistance(from);
        return (int) Math.round(distance / walkSpeed);
    }
}
