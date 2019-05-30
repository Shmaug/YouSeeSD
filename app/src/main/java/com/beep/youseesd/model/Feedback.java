package com.beep.youseesd.model;

public class Feedback {
    public int feedbackId; //could be String type
    public int tourId; //could be String type
    public String userId;
    public String message;
    public int rating;

    public Feedback() {
        this.feedbackId = 0;
        this. tourId = 0;
        this.userId = null;
        this.message = null;
        this.rating = 0;
    }

    public void addRating(int rating) {
        this.rating = rating;
    }
}
