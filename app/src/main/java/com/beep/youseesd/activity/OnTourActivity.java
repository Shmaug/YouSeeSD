package com.beep.youseesd.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.beep.youseesd.R;
import com.beep.youseesd.model.Tour;
import com.beep.youseesd.model.TourLocation;
import com.beep.youseesd.model.TourStop;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class OnTourActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private static final int PERMISSION_GRANTED_LOCATION = 0x01;
    private static final long INTERVAL = 2000;
    private static final long FASTEST_INTERVAL = 1000;

    private GoogleMap mMap;
    private Tour mTour;
    private float mTourProgress;

    private Criteria mCriteria;
    private LocationManager mLocationManager;

    private Location mCurrentLocation;
    private String mLocationProvider;

    private void LoadDefaultTour() {
        TourLocation geisel = new TourLocation("Geisel Library", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/geisel.jpg");
        TourLocation MedicalEducation = new TourLocation("Medical Education and Telemedicine building", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/Medical-Education-Telemedicine-Building.jpg");
        TourLocation radyManagement = new TourLocation("Rady School of Management", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/Rady-School-of-Management.jpg");
        TourLocation priceCenterWest = new TourLocation("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg");
        TourLocation centerHall = new TourLocation("Center Hall", "Big lecture hall", "https://act.ucsd.edu/m/assets/min/img.php?img=https://saber.ucsd.edu/m/campustours/Images/2-centerHall.jpg");
        TourLocation peterson = new TourLocation("Petersno Hall", "Big lecture hall", "http://ucsdguardian.org/wp-content/uploads/2014/02/ucsd010.jpg");

        mTour = new Tour("Default Tour", new TourStop[]{
                new TourStop(32.877974, -117.237469, centerHall),
                new TourStop(32.878154, -117.237549, null),
                new TourStop(32.879332, -117.237566, null),
                new TourStop(32.879777, -117.236958, priceCenterWest),
                new TourStop(32.880323, -117.236296, null),
                new TourStop(32.880364, -117.236587, null),
                new TourStop(32.880327, -117.237036, null),
                new TourStop(32.880275, -117.237557, geisel),
                new TourStop(32.880227, -117.237991, null),
                new TourStop(32.880197, -117.239942, peterson),
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LoadDefaultTour();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_GRANTED_LOCATION) {
            // check to see if fine location permission has been granted
            // should only be 1 permission here, so the loop should only run once
            boolean f = false;
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    f = true;
                    break;
                }
            }

            // use the permission to enable location on the map and find nearby locations
            if (f) {
                try {
                    mMap.setMyLocationEnabled(true);
                    BeginTour();
                } catch (SecurityException e) {
                }
            } else {
                // user doesn't want us to use their location :(
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            BeginTour();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GRANTED_LOCATION);
        }

        DrawTour();
    }

    public void BeginTour() {
        mTourProgress = 0f;

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mCriteria = new Criteria();
        mLocationProvider = String.valueOf(mLocationManager.getBestProvider(mCriteria, true));

        try {
            mLocationManager.requestLocationUpdates(mLocationProvider, 1000, 3.f, this);
            Location l = mLocationManager.getLastKnownLocation(mLocationProvider);
            if (l != null) onLocationChanged(l);
        } catch (SecurityException e) {
        }
    }

    public void DrawTour(){
        PolylineOptions route = new PolylineOptions();
        route.clickable(false);

        for (int i = 0; i < mTour.getNumStops(); i++){
            route.add(mTour.getStop(i).getCoords());
        }

        mMap.addPolyline(route);
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),15));

        // get the distance between the last and next stops
        Location l = mTour.getStop((int)mTourProgress).toLocation();
        Location n = mTour.getStop((int)mTourProgress + 1).toLocation();
        float distance = l.distanceTo(n);

        // calculate tour progress by progress towards next stop
        //mTourProgress = (int)mTourProgress + location.distanceTo(l) / distance;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
