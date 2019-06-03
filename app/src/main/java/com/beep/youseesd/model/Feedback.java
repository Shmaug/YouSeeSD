package com.beep.youseesd.model;

/**
 * Class that handles the feedback we get from a user
 */
public class Feedback {

  //could be String type
  private int feedbackId;
  //could be String type
  private int tourId;
  private String userId;
  private String message;
  private int rating;

  /**
   * No-arg constructor that sets these values to their default value
   */
  public Feedback() {
    this.setFeedbackId(0);
    this.setTourId(0);
    this.setUserId(null);
    this.setMessage(null);
    this.addRating(0);
  }

  /**
   * Getter for feedbackID
   *
   * @return feedbackID
   */
  public int getFeedbackId() {
    return feedbackId;
  }

  /**
   * Setter for feedbackID
   *
   * @param feedbackId the feedbackID to be set
   */
  public void setFeedbackId(int feedbackId) {
    this.feedbackId = feedbackId;
  }

  /**
   * Getter for tourID
   *
   * @return tourID
   */
  public int getTourId() {
    return tourId;
  }

  /**
   * Setter for tourID
   *
   * @param tourId the tourID to be set
   */
  public void setTourId(int tourId) {
    this.tourId = tourId;
  }

  /**
   * Getter for userID
   *
   * @return userID
   */
  public String getUserId() {
    return userId;
  }

  /**
   * Setter for userID
   *
   * @param userId the userID to be set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * Getter for message
   *
   * @return message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Setter for message
   *
   * @param message the message to be set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Getter for rating
   *
   * @return rating
   */
  public int getRating() {
    return rating;
  }

  /**
   * Setter for the rating once obtained from user
   *
   * @param rating the rating to set
   */
  public void addRating(int rating) {
    this.rating = rating;
  }
}
