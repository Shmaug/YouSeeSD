package com.beep.youseesd.model;

public class Feedback {

  public String feedbackId;
  public String tourId;
  public String userId;
  public String message;
  public int rating;

  public Feedback(String feedbackId, String tourId, String userId) {
    this.feedbackId = feedbackId;
    this.tourId = tourId;
    this.userId = userId;
    message = "";
    rating = 0;
  }
}
