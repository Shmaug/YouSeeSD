package com.beep.youseesd.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TourLocation implements Parcelable {

    public String title;
    public String subtitle;
    public String imageUrl;
    public long visitedTimestamp;

    public TourLocation(String title, String subtitle, String imageUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
        this.visitedTimestamp = 0;
    }

    public TourLocation(Parcel in) {
        title = in.readString();
        subtitle = in.readString();
        imageUrl = in.readString();
    }

    public boolean isVisited() {
        return visitedTimestamp > 0;
    }

    // parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeString(imageUrl);
    }

    public static final Creator<TourLocation> CREATOR = new Creator<TourLocation>() {
        @Override
        public TourLocation createFromParcel(Parcel in) {
            return new TourLocation(in);
        }

        @Override
        public TourLocation[] newArray(int size) {
            return new TourLocation[size];
        }
    };
}
