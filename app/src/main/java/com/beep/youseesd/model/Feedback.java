package com.beep.youseesd.model;

public class Feedback {
    String feedbackId;
    String tourId;
    String userId;
    String message;
    int rating;

    public Feedback(String feedbackId, String tourId, String userId) {
        this.feedbackId = feedbackId;
        this. tourId = tourId;
        this.userId = userId;
        message = "";
        rating = 0;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addRating(int rating) {
        this.rating = rating;
    }
}
