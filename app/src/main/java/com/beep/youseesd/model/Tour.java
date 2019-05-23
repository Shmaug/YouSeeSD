package com.beep.youseesd.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tour implements Parcelable {
    private long createdTime;
    private boolean isPausing;
    private String title;
    private String imageUrl;
    private String subtitle;
    private TourStop[] route;

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

    public TourStop getStop(int index) {
        return route[index];
    }

    public TourStop[] getStops() {
        return route;
    }

    public int getNumStops() {
        return route.length;
    }
}
