package com.beep.youseesd.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beep.youseesd.R;
import com.beep.youseesd.adapter.TourLocationManageAdapter;
import com.beep.youseesd.model.Tour;
import com.beep.youseesd.model.TourLocation;
import com.beep.youseesd.model.TourStop;
import com.beep.youseesd.util.WLog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OnTourActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
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

    private LinearLayout llBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    private TextView mBottomTitle;
    private TextView mBottomSubtitle;
    private LinearLayout mBottomSheetHeaderLayout;

    private RecyclerView mLocationManagerListView;
    private LinearLayoutManager layoutManager;
    private TourLocationManageAdapter mAdapter;

    List<Marker> mMarkers;

    private void loadDefaultTour() {
        TourLocation geisel = new TourLocation("Geisel Library", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/geisel.jpg");
        TourLocation MedicalEducation = new TourLocation("Medical Education and Telemedicine building", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/Medical-Education-Telemedicine-Building.jpg");
        TourLocation radyManagement = new TourLocation("Rady School of Management", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/Rady-School-of-Management.jpg");
        TourLocation priceCenterWest = new TourLocation("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg");
        TourLocation centerHall = new TourLocation("Center Hall", "Big lecture hall", "https://act.ucsd.edu/m/assets/min/img.php?img=https://saber.ucsd.edu/m/campustours/Images/2-centerHall.jpg");
        TourLocation peterson = new TourLocation("Petersno Hall", "Big lecture hall", "http://ucsdguardian.org/wp-content/uploads/2014/02/ucsd010.jpg");

        mTour = new Tour("Default Tour", new TourStop[]{
                new TourStop(32.877974, -117.237469, centerHall),
//                new TourStop(32.878154, -117.237549, null),
//                new TourStop(32.879332, -117.237566, null),
                new TourStop(32.879777, -117.236958, priceCenterWest),
//                new TourStop(32.880323, -117.236296, null),
//                new TourStop(32.880364, -117.236587, null),
//                new TourStop(32.880327, -117.237036, null),
                new TourStop(32.880275, -117.237557, geisel),
//                new TourStop(32.880227, -117.237991, null),
                new TourStop(32.880197, -117.239942, peterson),
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_show_locations:
                WLog.i("menu item clicked");
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tour, menu);
        menu.findItem(R.id.menu_show_locations).setIcon(
                new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_view_list_alt)
                        .actionBar().color(Color.WHITE)
        );
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mToolbar = (Toolbar) findViewById(R.id.tour_toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mMarkers = new ArrayList<>();
        loadDefaultTour();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.tour_drawer_layout);
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // to update the visited time in real-time.
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        });

        mNavigationView = (NavigationView) findViewById(R.id.tour_side_nav_layout);
        mBottomSheetHeaderLayout = (LinearLayout) findViewById(R.id.tour_bottom_sheet_header_layout);
        mBottomSheetHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        mLocationManagerListView = (RecyclerView) findViewById(R.id.tour_location_manage_list);
        layoutManager = new LinearLayoutManager(this);
        mLocationManagerListView.setLayoutManager(layoutManager);
        mAdapter = new TourLocationManageAdapter(this, mTour.getStops());
        mLocationManagerListView.setAdapter(mAdapter);

        mBottomTitle = (TextView) findViewById(R.id.tour_bottom_sheet_header_title);
        mBottomSubtitle = (TextView) findViewById(R.id.tour_bottom_sheet_header_subtitle);

        llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // get the bottom sheet view
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        mMap.getUiSettings().setAllGesturesEnabled(false);
                        break;

                    default:
                        mMap.getUiSettings().setAllGesturesEnabled(true);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

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

        drawTour();
    }

    public void beginTour() {
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

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setApiKey(getString(R.string.google_maps_key))
                .setConnectTimeout(5, TimeUnit.SECONDS)
                .setReadTimeout(5, TimeUnit.SECONDS)
                .setWriteTimeout(5, TimeUnit.SECONDS);
    }

    private DirectionsResult getDirectionResult(com.google.maps.model.LatLng origin, com.google.maps.model.LatLng destination) {
        try {
            DateTime now = new DateTime();
            DirectionsResult result = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.WALKING)
                    .origin(origin)
                    .destination(destination)
                    .departureTime(now)
                    .await();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private PolylineOptions addPolyline(DirectionsResult results) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
        PolylineOptions out = new PolylineOptions().addAll(decodedPath);
        return out;
    }

    public void drawTour() {
        PolylineOptions route = new PolylineOptions();
        route.color(getResources().getColor(R.color.primaryColor));
        route.clickable(false);

//        for (int i = 0; i < mTour.getNumStops(); i++) {
//            route.add(mTour.getStop(i).getCoords());
//        }
//        mMap.addPolyline(route);

        com.google.maps.model.LatLng origin = new com.google.maps.model.LatLng(mTour.getStop(0).getCoords().latitude, mTour.getStop(0).getCoords().longitude);
        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(mTour.getStop(1).getCoords().latitude, mTour.getStop(1).getCoords().longitude);
        WLog.i("origin: " + origin + ", destination: " + destination);
//        mMap.addPolyline(addPolyline(getDirectionResult(origin, destination)));

        for (TourStop t : mTour.getStops()) {
            MarkerOptions markerOptions = new MarkerOptions().position(t.getCoords())
                    .icon(getMarkerIconFromDrawable(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_pin)
                            .color(getResources().getColor(R.color.secondaryColor))
                            .sizeDp(42)));

//            mMarkers.add(m);
            Marker addedMarker = mMap.addMarker(markerOptions);
            mMarkers.add(addedMarker);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int id = Integer.parseInt(marker.getId().substring(1));
                updateBottomSheetCollapsed(mTour.getStop(id));
                return true;
            }
        });
    }


    public void updateLocationPinMarkerVisited(boolean visited, int i) {
        Marker m = mMarkers.get(i);
        LatLng loc = m.getPosition();

        MarkerOptions opt = new MarkerOptions().position(loc)
                .icon(getMarkerIconFromDrawable(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_pin)
                        .color(getResources().getColor(visited ? R.color.primaryColor : R.color.secondaryColor))
                        .sizeDp(42)));

        mMarkers.set(i, mMap.addMarker(opt));
    }

    private void updateBottomSheetCollapsed(TourStop stop) {
        mBottomTitle.setText(stop.getTourLocation().title);
        mBottomSubtitle.setText(stop.getTourLocation().subtitle);
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17));

        // get the distance between the last and next stops
        Location l = mTour.getStop((int) mTourProgress).toLocation();
        Location n = mTour.getStop((int) mTourProgress + 1).toLocation();
        float distance = l.distanceTo(n);

        // calculate tour progress by progress towards next stop
//        mTourProgress = (int) mTourProgress + location.distanceTo(l) / distance;
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
