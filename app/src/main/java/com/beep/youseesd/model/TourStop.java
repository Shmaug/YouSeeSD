package com.beep.youseesd.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class TourStop implements Parcelable {
    private TourLocation tourLocation;
    private LatLng coords;

    public TourStop(double latitude, double longitude, TourLocation tourLocation){
        coords = new LatLng(latitude, longitude);
        this.tourLocation = tourLocation;
    }
    public TourStop(LatLng coords, TourLocation tourLocation){
        this.coords = coords;
        this.tourLocation = tourLocation;
    }
    protected TourStop(Parcel in) {
        coords = new LatLng(in.readDouble(), in.readDouble());
        tourLocation = in.readParcelable(TourLocation.class.getClassLoader());
    }

    public LatLng getCoords() { return coords; }
    public Location toLocation() {
        Location l = new Location("");
        l.setLatitude(coords.latitude);
        l.setLongitude(coords.longitude);
        return l;
    }

    public static final Creator<TourStop> CREATOR = new Creator<TourStop>() {
        @Override
        public TourStop createFromParcel(Parcel in) {
            return new TourStop(in);
        }

        @Override
        public TourStop[] newArray(int size) {
            return new TourStop[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(coords.latitude);
        dest.writeDouble(coords.longitude);
        dest.writeParcelable(tourLocation, flags);
    }
}
