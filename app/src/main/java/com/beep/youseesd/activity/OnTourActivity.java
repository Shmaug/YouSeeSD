package com.beep.youseesd.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.ViewGroup;
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
import com.beep.youseesd.application.App;
import com.beep.youseesd.model.Tour;
import com.beep.youseesd.model.TourSet;
import com.beep.youseesd.util.DatabaseUtil;
import com.beep.youseesd.util.DistanceUtil;
import com.beep.youseesd.util.WLog;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.joda.time.DateTime;


public class OnTourActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, View.OnClickListener, GoogleMap.OnMapClickListener {
  private static final int PERMISSION_GRANTED_LOCATION = 0x01;
  private static final long INTERVAL = 2000;
  private static final long FASTEST_INTERVAL = 1000;
  private static final float ARRIVAL_DISTANCE = 10; // distance to "arrive" at a TourStop
  private static final float WANDER_DISTANCE = 15; // distance to wander away from tour path before warning

  private Location mUserCurrentLocation;
  private GoogleMap mMap;
  private Tour mTour;
  private float mTourProgress; // divide by (mTour.getNumStops() - 1) to get total tour percentage
  private Polyline mHintPolyline;
  private Polyline mPathPolyline;

  private Criteria mCriteria;
  private LocationManager mLocationManager;

  private String mLocationProvider;

  private LinearLayout llBottomSheet;
  private BottomSheetBehavior bottomSheetBehavior;
  private Toolbar mToolbar;
  private NavigationView mNavigationView;
  private DrawerLayout mDrawerLayout;

  private TextView mBottomTitle;
  private TextView mBottomSubtitle;
  private ViewGroup mBottomSheetHeaderLayout;
  private IconicsImageView mBottomImageView;

  private MaterialButton mMarkVisitedButton;
  private TextView mVisitText;
  private LinearLayout mTagContentLayout;
  private TextView mBottomPlaceDescription;

  private RecyclerView mLocationManagerListView;
  private LinearLayoutManager layoutManager;
  private TourLocationManageAdapter mAdapter;

  private LinearLayout mBottomPlaceDetailHashLayout;
  private LinearLayout mBottomPlaceDetailBuiltinLayout;
  private LinearLayout mBottomPlaceDetailSeatsLayout;
  private LinearLayout mBottomPlaceDetailCoursesLayout;

  private TextView mBottomPlaceHashTextView;
  private TextView mBottomPlaceBuiltinTextView;
  private TextView mBottomPlaceSeatsTextView;
  private TextView mBottomPlaceCoursesTextView;

  private int mSelectedTour = 0;
  List<Marker> mMarkers;

//  private void loadDefaultTour() {
//    TourLocation geisel = new TourLocation("Geisel Library", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/geisel.jpg");
//    TourLocation MedicalEducation = new TourLocation("Medical Education and Telemedicine building", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/Medical-Education-Telemedicine-Building.jpg");
//    TourLocation radyManagement = new TourLocation("Rady School of Management", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/Rady-School-of-Management.jpg");
//    TourLocation priceCenterWest = new TourLocation("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg");
//    TourLocation centerHall = new TourLocation("Center Hall", "Big lecture hall", "https://act.ucsd.edu/m/assets/min/img.php?img=https://saber.ucsd.edu/m/campustours/Images/2-centerHall.jpg");
//    TourLocation peterson = new TourLocation("Petersno Hall", "Big lecture hall", "http://ucsdguardian.org/wp-content/uploads/2014/02/ucsd010.jpg");
//
//    mTour = new Tour("Default Tour", new TourStop[]{
//        new TourStop(32.877974, -117.237469, centerHall),
////                new TourStop(32.878154, -117.237549, null),
////                new TourStop(32.879332, -117.237566, null),
//        new TourStop(32.879777, -117.236958, priceCenterWest),
////                new TourStop(32.880323, -117.236296, null),
////                new TourStop(32.880364, -117.236587, null),
////                new TourStop(32.880327, -117.237036, null),
//        new TourStop(32.880275, -117.237557, geisel),
////                new TourStop(32.880227, -117.237991, null),
//        new TourStop(32.880197, -117.239942, peterson),
//    });
//  }

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
//    drawTour();
  }

  private void endTour() {
    mTour = null;
    mLocationManager.removeUpdates(this);

    WLog.i("end tour clicked.");
    new MaterialAlertDialogBuilder(this).setTitle("Finish the tour?").setNegativeButton("Not yet", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {

      }
    }).setPositiveButton("Yes, I am done", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        finish();
      }
    }).create().show();
  }

  private void drawTour() {
    if (mPathPolyline != null) mPathPolyline.remove();
    PolylineOptions route = new PolylineOptions();
    route.clickable(false);

    for (int i = (int) mTourProgress; i < mTour.locations.size(); i++) {
      com.beep.youseesd.model.Location l = TourSet.allLocations.get(mTour.locations.get(i));
      route.add(new LatLng(l.latitude, l.longitude));
    }

    route.color(0xff55ff55);
    mPathPolyline = mMap.addPolyline(route);

    com.beep.youseesd.model.Location locOrigin = TourSet.allLocations.get(mTour.locations.get(0));
    com.beep.youseesd.model.Location locDestination = TourSet.allLocations.get(mTour.locations.get(1));

    LatLng origin = new LatLng(locOrigin.latitude, locOrigin.longitude);
    LatLng destination = new LatLng(locDestination.latitude, locDestination.longitude);
    WLog.i("origin: " + origin + ", destination: " + destination);
//        mMap.addPolyline(addPolyline(getDirectionResult(origin, destination)));

    for (String t : mTour.locations) {
      com.beep.youseesd.model.Location loc = TourSet.allLocations.get(t);
      MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(loc.latitude, loc.longitude))
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
        mSelectedTour = Integer.parseInt(marker.getId().substring(1));
        updateBottomSheetCollapsed(TourSet.allLocations.get(mTour.locations.get(mSelectedTour)));
        return true;
      }
    });
  }

  //private void stopReached(TourStop stop) {
  private void stopReached() {
    drawTour();
  }

  private void updateLocation(Location location) {
    if (mTour == null) return; // no active tour

    // get the distance between the last and next stops
    com.beep.youseesd.model.Location ourLoc = TourSet.allLocations.get(mTour.locations.get((int) mTourProgress));
    com.beep.youseesd.model.Location ourNextLoc = TourSet.allLocations.get(mTour.locations.get((int) mTourProgress));

    Location l = new Location("");
    l.setLatitude(ourLoc.latitude);
    l.setLongitude(ourLoc.longitude);

    Location n = new Location("");
    n.setLatitude(ourNextLoc.latitude);
    n.setLongitude(ourNextLoc.longitude);

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
    float theta = (float) Math.toRadians(Math.abs(b0 - b1));
    float p = x * (float) Math.cos(theta); // distance parallel along the path
    float q = x * (float) Math.sin(theta); // distance perpendicular away from the path

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
    } else {
      if (mHintPolyline != null) mHintPolyline.remove();
      mHintPolyline = null;
    }

    float t = Math.max(0f, Math.min(p / d, .99f)); // p / d shouldn't be > 1 but clamp just in case
    if (location.distanceTo(n) < ARRIVAL_DISTANCE) {
      mTourProgress = (int) mTourProgress + 1;
      //stopReached(mTour.getStop((int) mTourProgress));
      stopReached();
      if (mTourProgress == mTour.locations.size() - 1) {
        endTour();
      }
    } else
      mTourProgress = (int) mTourProgress + t;
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


  // sets up option menu icon
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

    mBottomPlaceDetailHashLayout = (LinearLayout) findViewById(R.id.bottom_place_detail_hash_layout);
    mBottomPlaceDetailBuiltinLayout = (LinearLayout) findViewById(R.id.bottom_place_detail_builtin_layout);
    mBottomPlaceDetailCoursesLayout = (LinearLayout) findViewById(R.id.bottom_place_detail_courses_layout);
    mBottomPlaceDetailSeatsLayout = (LinearLayout) findViewById(R.id.bottom_place_detail_seats_layout);
    mBottomPlaceBuiltinTextView = (TextView) findViewById(R.id.tour_bottom_builtin_year);
    mBottomPlaceSeatsTextView = (TextView) findViewById(R.id.tour_bottom_tags_seats);
    mBottomPlaceCoursesTextView = (TextView) findViewById(R.id.tour_bottom_classes);
    mBottomPlaceHashTextView = (TextView) findViewById(R.id.tour_bottom_tags);

    mTagContentLayout = (LinearLayout) findViewById(R.id.tour_bottom_tags_layout);
    mBottomImageView = (IconicsImageView) findViewById(R.id.tour_bottom_image);
    mMarkVisitedButton = (MaterialButton) findViewById(R.id.tour_visit_mark_btn);
    mVisitText = (TextView) findViewById(R.id.tour_bottom_visit_text);
    mMarkVisitedButton.setOnClickListener(this);
    mNavigationView = (NavigationView) findViewById(R.id.tour_side_nav_layout);
    mBottomPlaceDescription = (TextView) findViewById(R.id.tour_bottom_description);
    mBottomSheetHeaderLayout = (ViewGroup) findViewById(R.id.tour_bottom_sheet_header_layout);
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
            showPlaceDetails(mSelectedTour);
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

  private void loadLocations(String uid, String tourId) {
    DatabaseReference tourRef = DatabaseUtil.getSingleTourDatabase(uid, tourId);
    tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Tour t = dataSnapshot.getValue(Tour.class);
        if (t == null) {
          return;
        }

        mTour = t;
        mAdapter = new TourLocationManageAdapter(OnTourActivity.this, mTour);
        mLocationManagerListView.setAdapter(mAdapter);
        System.out.println("HI: " + t);
        drawPathPoints(t);
        System.out.println("BYE: " + t);
        addPlacePinsOnMap(t);
        drawTour();

        // for default location
        updateBottomSheetCollapsed(TourSet.allLocations.get(mTour.locations.get(0)));
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  private void addPlacePinsOnMap(Tour t) {
    for (String location : t.locations) {
      if (location == null) {
        continue;
      }
      com.beep.youseesd.model.Location l = TourSet.allLocations.get(location);
      System.out.println("location object: " + l);
      MarkerOptions markerOptions = new MarkerOptions().position(l.generateLatLng())
          .icon(getMarkerIconFromDrawable(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_pin)
              .color(getResources().getColor(R.color.secondaryColor))
              .sizeDp(42)));

      Marker addedMarker = mMap.addMarker(markerOptions);
      mMarkers.add(addedMarker);
    }
  }

  private void drawPathPoints(Tour t) {
//    PolylineOptions route = new PolylineOptions();
//    route.color(getResources().getColor(R.color.secondaryColor));
//    route.clickable(false);
//
//    for (com.beep.youseesd.model.Location l : t.locations) {
//      LatLng coords = new LatLng(l.latitude, l.longitude);
//      route.add(coords);
//      mMap.addPolyline(route);
//    }
  }

  @Override
  public void onBackPressed() {
    if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
      bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
      return;
    }

    super.onBackPressed();
  }

  private void showPlaceDetails(int id) {
    if (id < 0) {
      return;
    }

    com.beep.youseesd.model.Location l = TourSet.allLocations.get(mTour.locations.get(id));
    Glide.with(this)
        .load(l.imageUrl)
        .centerCrop()
        .into(mBottomImageView);

    mBottomPlaceDescription.setText(l.description);

    mBottomPlaceDetailSeatsLayout.setVisibility(l.seats != null && !l.seats.isEmpty() ? View.VISIBLE : View.GONE);
    mBottomPlaceDetailHashLayout.setVisibility(l.subtitle != null && !l.subtitle.isEmpty() ? View.VISIBLE : View.GONE);
    mBottomPlaceDetailBuiltinLayout.setVisibility(l.builtin != null && !l.builtin.isEmpty() ? View.VISIBLE : View.GONE);
    mBottomPlaceDetailCoursesLayout.setVisibility(l.courses != null && !l.courses.isEmpty() ? View.VISIBLE : View.GONE);

    mBottomPlaceSeatsTextView.setText(l.seats);
    mBottomPlaceHashTextView.setText(l.subtitle);
    mBottomPlaceBuiltinTextView.setText(l.builtin);
    mBottomPlaceCoursesTextView.setText(l.courses);
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
//    drawTour();

    final String tourId = getIntent().getStringExtra(DatabaseUtil.TOUR_ID);
//    String uid = "jDbXXUNhVuSHE3y8tEbNNVZfzpJ3";
    String uid = App.getUser().getUid();
    loadLocations(uid, tourId);

    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
      @Override
      public boolean onMarkerClick(Marker marker) {
        mSelectedTour = Integer.parseInt(marker.getId().substring(1));
        updateBottomSheetCollapsed(TourSet.allLocations.get(mTour.locations.get(mSelectedTour)));
        return true;
      }
    });
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

  public void updateLocationPinMarkerVisited(boolean visited, int i) {
    Marker m = mMarkers.get(i);
    LatLng loc = m.getPosition();

    MarkerOptions opt = new MarkerOptions().position(loc)
        .icon(getMarkerIconFromDrawable(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_pin)
            .color(getResources().getColor(visited ? R.color.primaryColor : R.color.secondaryColor))
            .sizeDp(42)));

    mMarkers.set(i, mMap.addMarker(opt));
  }

  public void updateBottomSheetCollapsed(com.beep.youseesd.model.Location l) {
    mBottomTitle.setText(l.title);
    mBottomSubtitle.setText(l.subtitle);
    mMarkVisitedButton.setTextColor(getResources().getColor(l.isVisited() ? R.color.light_gray : R.color.primaryColor));
    mMarkVisitedButton.setText(l.isVisited() ? "MARK UNVISITED" : "MARK VISITED");
//    mVisitText.setText(l.isVisited() ? "Visited " + l.calculateVisitedAgo() + " mins ago" : "0.7 miles away");

    String subtitle = generateSubtitleAccordingly(mUserCurrentLocation, l);
    mVisitText.setText(subtitle);
  }

  private String generateSubtitleAccordingly(Location userLocation, com.beep.youseesd.model.Location destination) {
    if (!destination.isVisited()) {
      float[] result = new float[1];
      Location.distanceBetween(userLocation.getLatitude(), userLocation.getLongitude(), destination.latitude, destination.longitude, result);
      double d = DistanceUtil.toMiles(result[0] / 1000);
      String mile = String.format("%.1f", d);
      return mile + " mile away";
    }

    return destination.calculateVisitedAgo() + " mins";
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
    mUserCurrentLocation = location;
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17));

    updateLocation(location);
    // get the distance between the last and next stops
//        Location l = mTour.getStop((int) mTourProgress).toLocation();
//        Location n = mTour.getStop((int) mTourProgress + 1).toLocation();
//        float distance = l.distanceTo(n);

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

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tour_visit_mark_btn:
      case R.id.item_tour_location_layout:
        if (mSelectedTour < 0) {
          break;
        }
        com.beep.youseesd.model.Location t = TourSet.allLocations.get(mTour.locations.get(mSelectedTour));
        if (!t.isVisited()) {
          t.setVisited();
        } else {
          t.setUnvisited();
        }

        mAdapter.notifyDataSetChanged();
        updateLocationPinMarkerVisited(t.isVisited(), mSelectedTour);
        updateBottomSheetCollapsed(TourSet.allLocations.get(mTour.locations.get(mSelectedTour)));
        break;

      case R.id.item_end_tour_btn:
        endTour();
        break;
    }
  }

  @Override
  public void onMapClick(LatLng latLng) {
    //Location l = new Location(mLocationProvider);
    //l.setLatitude(latLng.latitude);
    //l.setLongitude(latLng.longitude);
    //updateLocation(l);
  }
}
