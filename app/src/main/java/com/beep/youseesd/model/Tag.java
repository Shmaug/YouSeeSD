package com.beep.youseesd.model;

public class Tag {
    private String tagId;
    private String tagName;

    public Tag(String tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public String getTagId() {
        return this.tagId;
    }

    public String getTagName() {
        return this.tagName;
    }
}
