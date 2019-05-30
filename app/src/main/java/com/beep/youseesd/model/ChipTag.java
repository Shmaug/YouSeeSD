package com.beep.youseesd.model;

public class ChipTag {
    private Tag tag;
    private boolean isSelected;

    public ChipTag(Tag tag) {
        this.tag = tag;
        isSelected = false;
    }

    // For default (aka "general") chiptag
    public ChipTag(Tag tag, boolean isSelected) {
        this.tag = tag;
        this.isSelected = isSelected;
    }

    public void toggleSelection() {
        this.isSelected = !this.isSelected;
    }

    public boolean getIsSelection() {
        return this.isSelected;
    }
}
