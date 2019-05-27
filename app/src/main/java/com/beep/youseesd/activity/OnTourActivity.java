package com.beep.youseesd.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.beep.youseesd.R;
import com.beep.youseesd.model.Tour;
import com.beep.youseesd.model.TourLocation;
import com.beep.youseesd.model.TourStop;
import com.beep.youseesd.util.WLog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

public class OnTourActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapClickListener {
    private static final int PERMISSION_GRANTED_LOCATION = 0x01;
    private static final long INTERVAL = 2000;
    private static final long FASTEST_INTERVAL = 1000;
    private static final float ARRIVAL_DISTANCE = 10; // distance to "arrive" at a TourStop
    private static final float WANDER_DISTANCE = 15; // distance to wander away from tour path before warning

    private GoogleMap mMap;
    private Tour mTour;
    private float mTourProgress; // divide by (mTour.getNumStops() - 1) to get total tour percentage
    private Polyline mHintPolyline;
    private Polyline mPathPolyline;

    private Criteria mCriteria;
    private LocationManager mLocationManager;

    private String mLocationProvider;

    private void loadDefaultTour() {
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

    private void beginTour() {
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

        mMap.setOnMapClickListener(this);
        drawTour();
    }

    private void endTour(){
        mTour = null;
        mLocationManager.removeUpdates(this);
        // TODO: switch screen
    }

    private void drawTour() {
        if (mPathPolyline != null) mPathPolyline.remove();
        PolylineOptions route = new PolylineOptions();
        route.clickable(false);

        for (int i = (int)mTourProgress; i < mTour.getNumStops(); i++)
            route.add(mTour.getStop(i).getCoords());

        route.color(0xff55ff55);
        mPathPolyline = mMap.addPolyline(route);
    }

    private void stopReached(TourStop stop){
        // TODO: pop something up on the screen
        drawTour();
    }

    private void updateLocation(Location location){
        if (mTour == null) return; // no active tour

        // get the distance between the last and next stops
        Location l = mTour.getStop((int)mTourProgress).toLocation();
        Location n = mTour.getStop((int)mTourProgress + 1).toLocation();

        /*
             location
                *
              /  |
             /   |
          x /    | q
           /     |
          /theta |
        l -------- --------- n
              p
                 d (l to n)
        */

        float d = l.distanceTo(n);
        float x = location.distanceTo(l);
        float b0 = l.bearingTo(location);
        float b1 = l.bearingTo(n);
        float theta = (float)Math.toRadians(Math.abs(b0 - b1));
        float p = x * (float)Math.cos(theta); // distance parallel along the path
        float q = x * (float)Math.sin(theta); // distance perpendicular away from the path

        if (theta > Math.PI * .5 || // user is behind previous stop... (going the wrong way?)
                q > WANDER_DISTANCE) { // user is far from the route line

            if (mHintPolyline != null) mHintPolyline.remove();

            PolylineOptions hint = new PolylineOptions();
            hint.clickable(false);
            hint.add(new LatLng(location.getLatitude(), location.getLongitude()));
            hint.add(new LatLng(l.getLatitude(), l.getLongitude()));

            hint.color(0xffaa0000);
            hint.endCap(new RoundCap());
            hint.startCap(new RoundCap());
            hint.width(5f);

            mHintPolyline = mMap.addPolyline(hint);
        }
        else
        {
            if (mHintPolyline != null) mHintPolyline.remove();
            mHintPolyline = null;
        }

        float t = Math.max(0f, Math.min(p / d, .99f)); // p / d shouldn't be > 1 but clamp just in case
        if (location.distanceTo(n) < ARRIVAL_DISTANCE){
            mTourProgress = (int)mTourProgress + 1;
            stopReached(mTour.getStop((int)mTourProgress));
            if (mTourProgress == mTour.getNumStops() - 1){
                endTour();
            }
        } else
            mTourProgress = (int)mTourProgress + t;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        loadDefaultTour();
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
                    beginTour();
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
            beginTour();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GRANTED_LOCATION);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
        updateLocation(location);
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

    @Override
    public void onMapClick(LatLng latLng) {
        Location l = new Location(mLocationProvider);
        l.setLatitude(latLng.latitude);
        l.setLongitude(latLng.longitude);
        //updateLocation(l);
    }
}
