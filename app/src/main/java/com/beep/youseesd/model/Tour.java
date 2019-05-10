package com.beep.youseesd.model;

public class Tour {
    private long createdTime;
    private boolean isPausing;
    private String title;

    public Tour() {

    }

    public Tour(String title) {
        setTitle(title);
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public String getTitle() {
        return title;
    }
}
