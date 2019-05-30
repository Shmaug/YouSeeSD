package com.beep.youseesd.model;

import java.util.ArrayList;
import java.util.List;

public class PathPoint {

  // value is temporary and must be changed
  private static final double WALK_SPEED = 1.5;

  public double latitude;
  public double longitude;
  public List<PathPoint> neighbors;

  public PathPoint() {
    latitude = 0d;
    longitude = 0d;
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

  public void setNeighborList(ArrayList<PathPoint> neighbors) {
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

  public List<PathPoint> getNeighborList() {
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
    return (int) Math.round(distance / WALK_SPEED);
  }

  public PathPoint findNearestPathPoint(ArrayList<PathPoint> list) {
    // arbitrary large distance
    double shortestDistance = 9999999.9;
    PathPoint nearestPathPoint = null;

    // iterate only once
    for (PathPoint p : list) {
      if (this.getDistance(p) < shortestDistance) {
        shortestDistance = this.getDistance(p);
        nearestPathPoint = p;
      }
    }

    return nearestPathPoint;
  }
}
