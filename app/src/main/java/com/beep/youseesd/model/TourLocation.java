package com.beep.youseesd.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TourLocation implements Parcelable {
    public String title;
    public String subtitle;
    public String imageUrl;

    public TourLocation(String title, String subtitle, String imageUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
    }
    public TourLocation(Parcel in) {
        title = in.readString();
        subtitle = in.readString();
        imageUrl = in.readString();
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
