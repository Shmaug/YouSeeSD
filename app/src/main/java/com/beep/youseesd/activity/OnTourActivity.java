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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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

/**
 * Activity that handles the screen when we're on the tour
 */
public class OnTourActivity extends AppCompatActivity
    implements OnMapReadyCallback, LocationListener, View.OnClickListener,
    GoogleMap.OnMapClickListener {
  private static final int PERMISSION_GRANTED_LOCATION = 0x01;

  // distance to "arrive" at a Location
  private static final float ARRIVAL_DISTANCE = 10;
  // distance to wander away from tour path before warning
  private static final float WANDER_DISTANCE = 15;

  private GoogleMap mMap;
  private Tour mTour;

  // divide by (mTour.getNumStops() - 1) to get total tour percentage
  private float mTourProgress;
  private Polyline mHintPolyline;
  private Polyline mPathPolyline;

  private LocationManager mLocationManager;

  private BottomSheetBehavior bottomSheetBehavior;
  private DrawerLayout mDrawerLayout;

  private TextView mBottomTitle;
  private TextView mBottomSubtitle;
  private IconicsImageView mBottomImageView;

  private MaterialButton mMarkVisitedButton;
  private TextView mVisitText;
  private TextView mBottomPlaceDescription;

  private RecyclerView mLocationManagerListView;
  private TourLocationManageAdapter mAdapter;

  private LinearLayout mBottomPlaceDetailHashLayout;
  private LinearLayout mBottomPlaceDetailBuiltinLayout;
  private LinearLayout mBottomPlaceDetailSeatsLayout;
  private LinearLayout mBottomPlaceDetailCoursesLayout;

  private TextView mBottomPlaceHashTextView;
  private TextView mBottomPlaceBuiltinTextView;
  private TextView mBottomPlaceSeatsTextView;
  private TextView mBottomPlaceCoursesTextView;

  private int mSelectedTour = -1;
  List<Marker> mMarkers;

  /**
   * Handles logic to begin our tour once screen is loaded
   */
  private void beginTour() {
    mTourProgress = 0f;

    mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    Criteria mCriteria = new Criteria();
    String mLocationProvider = String.valueOf(mLocationManager
        .getBestProvider(mCriteria, true));

    // get the location of the user
    try {
      mLocationManager.requestLocationUpdates(mLocationProvider, 1000, 3.f, this);
      Location l = mLocationManager.getLastKnownLocation(mLocationProvider);
      if (l != null) onLocationChanged(l);
    } catch (SecurityException e) {
      WLog.d(e.getMessage());
    }

    // sets the click listener on our map to this class
    mMap.setOnMapClickListener(this);
  }

  /**
   * Handles logic when ending our tour
   */
  private void endTour() {
    mTour = null;
    mLocationManager.removeUpdates(this);
    WLog.i("end tour clicked.");

    // create the dialog alert that allows the user to end the tour
    new MaterialAlertDialogBuilder(this).setTitle("Finish the tour?")
        .setNegativeButton("Not yet", (dialog, which) -> {

        })
        .setPositiveButton("Yes, I am done", (dialog, which) -> finish())
        .create()
        .show();
  }

  /**
   * Handles the drawing logic of our tours
   */
  private void drawTour() {
    if (mPathPolyline != null) {
      mPathPolyline.remove();
    }

    PolylineOptions route = new PolylineOptions();
    route.clickable(false);

    // go through all of the locations on our tour and add a LatLng object for each one
    for (int i = (int) mTourProgress; i < mTour.locations.size(); i++) {
      com.beep.youseesd.model.Location l = TourSet.allLocations.get(mTour.locations.get(i));
      route.add(new LatLng(l.latitude, l.longitude));
    }

    // set the route's color
    route.color(0xff55ff55);
    mPathPolyline = mMap.addPolyline(route);

    // get the starting and ending location on the tour
    com.beep.youseesd.model.Location locOrigin =
        TourSet.allLocations.get(mTour.locations.get(0));
    com.beep.youseesd.model.Location locDestination =
        TourSet.allLocations.get(mTour.locations.get(1));

    // create LatLng objects for both of them
    LatLng origin = new LatLng(locOrigin.latitude, locOrigin.longitude);
    LatLng destination = new LatLng(locDestination.latitude, locDestination.longitude);
    WLog.i("origin: " + origin + ", destination: " + destination);

    // go through all of the locations on our tour and draw a marker for each one
    for (String t : mTour.locations) {
      com.beep.youseesd.model.Location loc = TourSet.allLocations.get(t);

      // create the marker with these options
      MarkerOptions markerOptions =
          new MarkerOptions().position(new LatLng(loc.latitude, loc.longitude))
              .icon(getMarkerIconFromDrawable(
                  new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_pin)
                      .color(getResources().getColor(R.color.secondaryColor))
                      .sizeDp(42)));

      Marker addedMarker = mMap.addMarker(markerOptions);
      mMarkers.add(addedMarker);
    }

    // create a listener for each marker so we can update the bottom sheet
    mMap.setOnMarkerClickListener(marker -> {
      mSelectedTour = Integer.parseInt(marker.getId().substring(1));
      updateBottomSheetCollapsed(TourSet.allLocations.get(mTour.locations.get(mSelectedTour)));
      return true;
    });
  }

  /**
   * Redraws tour once we've reached a location
   */
  private void stopReached() {
    drawTour();
  }

  /**
   * Updates our location while we're on the tour
   *
   * @param location our current location
   */
  private void updateLocation(Location location) {
    // no active tour
    if (mTour == null) {
      return;
    }

    // get the distance between the last and next stops
    com.beep.youseesd.model.Location ourLoc =
        TourSet.allLocations.get(mTour.locations.get((int) mTourProgress));
    com.beep.youseesd.model.Location ourNextLoc =
        TourSet.allLocations.get(mTour.locations.get((int) mTourProgress));

    // create our location objects based on the latitude and longitudes of our locations
    Location l = new Location("");
    l.setLatitude(ourLoc.latitude);
    l.setLongitude(ourLoc.longitude);

    Location n = new Location("");
    n.setLatitude(ourNextLoc.latitude);
    n.setLongitude(ourNextLoc.longitude);

    /* Diagram to help visualize the math for our drawing

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

    // distance parallel along the path
    float p = x * (float) Math.cos(theta);
    // distance perpendicular away from the path
    float q = x * (float) Math.sin(theta);

    // user is behind previous stop... (going the wrong way?)
    // or user is far from the route line
    if (theta > Math.PI * .5 || q > WANDER_DISTANCE) {
      if (mHintPolyline != null) {
        mHintPolyline.remove();
      }

      // path to help the user back on track
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
      // remove if unnecessary
      if (mHintPolyline != null) {
        mHintPolyline.remove();
      }
      mHintPolyline = null;
    }

    // p / d shouldn't be > 1 but clamp just in case
    float t = Math.max(0f, Math.min(p / d, .99f));

    // redraw once we've reached a location
    if (location.distanceTo(n) < ARRIVAL_DISTANCE) {
      mTourProgress = (int) mTourProgress + 1;
      stopReached();
      if (mTourProgress == mTour.locations.size() - 1) {
        endTour();
      }
    } else {
      mTourProgress = (int) mTourProgress + t;
    }
  }

  /**
   * Lifecycle method to handle the menu in the top right
   *
   * @param item the button to open the menu drawer
   * @return true if we were able to open the menu
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_show_locations) {
      WLog.i("menu item clicked");
      mDrawerLayout.openDrawer(Gravity.RIGHT);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * Lifecycle method that sets up the option menu icon
   *
   * @param menu the menu we want to display
   * @return true if we were able to load the menu
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_tour, menu);
    menu.findItem(R.id.menu_show_locations).setIcon(
        new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_view_list_alt)
            .actionBar().color(Color.WHITE)
    );
    return true;
  }

  /**
   * Lifecycle method that handles the creation of the map layout
   *
   * @param savedInstanceState state that is used in the super's onCreate method
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);

    // create the toolbar at the bottom of the screen
    Toolbar mToolbar = findViewById(R.id.tour_toolbar);
    mToolbar.setTitleTextColor(Color.WHITE);
    mToolbar.setTitle("");
    setSupportActionBar(mToolbar);

    // setup the map fragment on our screen
    SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    // initialize our markers list and setup the menu drawer layout
    mMarkers = new ArrayList<>();
    mDrawerLayout = findViewById(R.id.tour_drawer_layout);
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

    // link layouts to instance variables
    mBottomPlaceDetailHashLayout = findViewById(R.id.bottom_place_detail_hash_layout);
    mBottomPlaceDetailBuiltinLayout = findViewById(R.id.bottom_place_detail_builtin_layout);
    mBottomPlaceDetailCoursesLayout = findViewById(R.id.bottom_place_detail_courses_layout);
    mBottomPlaceDetailSeatsLayout = findViewById(R.id.bottom_place_detail_seats_layout);
    mBottomPlaceBuiltinTextView = findViewById(R.id.tour_bottom_builtin_year);
    mBottomPlaceSeatsTextView = findViewById(R.id.tour_bottom_tags_seats);
    mBottomPlaceCoursesTextView = findViewById(R.id.tour_bottom_classes);
    mBottomPlaceHashTextView = findViewById(R.id.tour_bottom_tags);

    // link more UI elements
    mBottomImageView = findViewById(R.id.tour_bottom_image);
    mMarkVisitedButton = findViewById(R.id.tour_visit_mark_btn);
    mVisitText = findViewById(R.id.tour_bottom_visit_text);
    mMarkVisitedButton.setOnClickListener(this);
    mBottomPlaceDescription = findViewById(R.id.tour_bottom_description);

    // set up listener for the bottom sheet
    ViewGroup mBottomSheetHeaderLayout = findViewById(R.id.tour_bottom_sheet_header_layout);
    mBottomSheetHeaderLayout.setOnClickListener(v -> {
      if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
      } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
      }
    });

    // link more UI elements to variables
    mLocationManagerListView = findViewById(R.id.tour_location_manage_list);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    mLocationManagerListView.setLayoutManager(layoutManager);

    mBottomTitle = findViewById(R.id.tour_bottom_sheet_header_title);
    mBottomSubtitle = findViewById(R.id.tour_bottom_sheet_header_subtitle);

    LinearLayout llBottomSheet = findViewById(R.id.bottom_sheet);
    bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

    // get the bottom sheet view and add a call back depending on the state of the sheet
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

  /**
   * Loads our locations on the map using our uid and tourId
   *
   * @param uid the user's id in firebase
   * @param tourId the id of the tour we want to load
   */
  private void loadLocations(String uid, String tourId) {
    // get the tour data from firebase
    DatabaseReference tourRef = DatabaseUtil.getSingleTourDatabase(uid, tourId);
    tourRef.addListenerForSingleValueEvent(new ValueEventListener() {

      /**
       * Firebase method that grabs data for us
       *
       * @param dataSnapshot the data stored in a dataSnapshot
       */
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // parse the snapshot as a Tour object
        Tour t = dataSnapshot.getValue(Tour.class);
        // return if unable to do so
        if (t == null) {
          return;
        }

        // use our adapter to populate our tour activity
        mTour = t;
        mAdapter = new TourLocationManageAdapter(OnTourActivity.this, mTour);
        mLocationManagerListView.setAdapter(mAdapter);

        // draw the markers and paths on the map
        addPlacePinsOnMap(t);
        drawTour();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  /**
   * Place pins on our map
   *
   * @param t the tour we're on
   */
  private void addPlacePinsOnMap(Tour t) {
    for (String location : t.locations) {
      if (location == null) {
        continue;
      }

      // get the associated Location object (our definition of a Location, not Android's)
      com.beep.youseesd.model.Location l = TourSet.allLocations.get(location);
      // place a marker with the specified options
      MarkerOptions markerOptions = new MarkerOptions().position(l.generateLatLng())
          .icon(
              getMarkerIconFromDrawable(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_pin)
                  .color(getResources()
                      .getColor(R.color.secondaryColor))
                  .sizeDp(42)));

      Marker addedMarker = mMap.addMarker(markerOptions);
      mMarkers.add(addedMarker);
    }
  }

  /**
   * Handles the behavior of the bottom sheet if the back button is pressed
   */
  @Override
  public void onBackPressed() {
    if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
      bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
      return;
    }

    super.onBackPressed();
  }

  /**
   * Renders the information on the bottom sheet based on the location that we've selected
   *
   * @param id the id of the location
   */
  private void showPlaceDetails(int id) {
    if (id < 0) {
      return;
    }

    // get the appropriate location using the given id
    com.beep.youseesd.model.Location l = TourSet.allLocations.get(mTour.locations.get(id));
    Glide.with(this)
        .load(l.imageUrl)
        .centerCrop()
        .into(mBottomImageView);

    // populate the subtitle with text
    mBottomPlaceDescription.setText(l.subtitle);

    // display visibility based on the input we're using
    mBottomPlaceDetailSeatsLayout.setVisibility(
        l.seats != null && !l.seats.isEmpty() ? View.VISIBLE : View.GONE);
    mBottomPlaceDetailHashLayout.setVisibility(
        l.tags != null && !l.tags.isEmpty() ? View.VISIBLE : View.GONE);
    mBottomPlaceDetailBuiltinLayout.setVisibility(
        l.builtin != null && !l.builtin.isEmpty() ? View.VISIBLE : View.GONE);
    mBottomPlaceDetailCoursesLayout.setVisibility(
        l.courses != null && !l.courses.isEmpty() ? View.VISIBLE : View.GONE);

    // populate layouts with the appropriate text
    mBottomPlaceSeatsTextView.setText(l.seats);
    mBottomPlaceHashTextView.setText(l.description);
    mBottomPlaceBuiltinTextView.setText(l.builtin);
    mBottomPlaceCoursesTextView.setText(l.courses);
  }

  /**
   * Requests permission to use the user's location for our app
   *
   * @param requestCode the code that represents the user's choice
   * @param permissions the array of Strings that represent our permissions
   * @param grantResults the array of ints that represent our permissions
   */
  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {

    // check to see if fine location permission has been granted
    // should only be 1 permission here, so the loop should only run once
    if (requestCode == PERMISSION_GRANTED_LOCATION) {
      boolean permission = false;

      for (int i = 0; i < permissions.length; i++) {
        if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)
            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
          permission = true;
          break;
        }
      }

      // use the permission to enable location on the map and find nearby locations
      if (permission) {
        try {
          mMap.setMyLocationEnabled(true);
          beginTour();
        } catch (SecurityException e) {
        }
      }
    }
  }

  /**
   * Called once the map is ready for use
   *
   * @param googleMap the map that we now have access to
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    // setup the options that we want
    mMap.setBuildingsEnabled(true);
    mMap.setIndoorEnabled(true);
    mMap.getUiSettings().setZoomControlsEnabled(true);
    mMap.getUiSettings().setCompassEnabled(true);
    mMap.getUiSettings().setIndoorLevelPickerEnabled(true);

    // check to see if we have permission from the user to use their location
    // if not, ask for it
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
      mMap.setMyLocationEnabled(true);
      beginTour();
    } else {
      ActivityCompat.requestPermissions(this,
          new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GRANTED_LOCATION);
    }

    // load the map with the locations on the tour that we're passing
    final String tourId = getIntent().getStringExtra(DatabaseUtil.TOUR_ID);
    String uid = App.getUser().getUid();
    loadLocations(uid, tourId);

    // register click listener on the map
    mMap.setOnMarkerClickListener(marker -> {
      mSelectedTour = Integer.parseInt(marker.getId().substring(1));
      updateBottomSheetCollapsed(TourSet.allLocations.get(mTour.locations.get(mSelectedTour)));
      return true;
    });
  }

  /**
   * Updates the markers to be a different color if we've visited them
   *
   * @param visited true if we've visited, false if we haven't
   * @param i the location that we're setting
   */
  public void updateLocationPinMarkerVisited(boolean visited, int i) {
    // get the marker using i
    Marker m = mMarkers.get(i);
    LatLng loc = m.getPosition();

    // change its appearance based on the value of visited
    MarkerOptions opt = new MarkerOptions().position(loc)
        .icon(getMarkerIconFromDrawable(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_pin)
            .color(getResources().getColor(visited ? R.color.primaryColor : R.color.secondaryColor))
            .sizeDp(42)));

    mMarkers.set(i, mMap.addMarker(opt));
  }

  /**
   * Update the bottom sheet based on the location that we've selected
   *
   * @param l the location that we've clicked on
   */
  public void updateBottomSheetCollapsed(com.beep.youseesd.model.Location l) {
    // update the display appropriately
    mBottomTitle.setText(l.title);
    mBottomSubtitle.setText(l.subtitle);
    mMarkVisitedButton.setTextColor(
        getResources().
            getColor(l.isVisited() ? R.color.light_gray : R.color.primaryColor));

    mMarkVisitedButton.setText(l.isVisited() ? "MARK UNVISITED" : "MARK VISITED");
    mVisitText.setText(
        l.isVisited() ? "Visited " + l.calculateVisitedAgo() + " mins ago" : "0.7 miles away");
  }

  /**
   * Gets the marker from the menu
   *
   * @param drawable the item that we've selected
   * @return a BitmapDescriptor that represents the marker
   */
  private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
    Canvas canvas = new Canvas();
    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
        Bitmap.Config.ARGB_8888);

    canvas.setBitmap(bitmap);
    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    drawable.draw(canvas);
    return BitmapDescriptorFactory.fromBitmap(bitmap);
  }

  /**
   * Listener for when our location changes
   *
   * @param location our new location
   */
  @Override
  public void onLocationChanged(Location location) {
    // move the camera appropriately and call on our updateLocation helper
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
        new LatLng(location.getLatitude(), location.getLongitude()), 17));

    updateLocation(location);
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) { }

  @Override
  public void onProviderEnabled(String provider) { }

  @Override
  public void onProviderDisabled(String provider) { }

  /**
   * Listener for click actions on our map
   *
   * @param v the item that was clicked on our activity
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      // handles logic to set locations as visited
      case R.id.tour_visit_mark_btn:
      case R.id.item_tour_location_layout:
        if (mSelectedTour < 0) {
          break;
        }

        // get the location that we tapped on
        com.beep.youseesd.model.Location t =
            TourSet.allLocations.get(mTour.locations.get(mSelectedTour));

        if (!t.isVisited()) {
          t.setVisited();
        } else {
          t.setUnvisited();
        }

        // update views and adapter as necessary
        mAdapter.notifyDataSetChanged();
        updateLocationPinMarkerVisited(t.isVisited(), mSelectedTour);
        updateBottomSheetCollapsed(TourSet.allLocations.get(mTour.locations.get(mSelectedTour)));
        break;

      // end the tour if that's what the user desires
      case R.id.item_end_tour_btn:
        endTour();
        break;
    }
  }

  @Override
  public void onMapClick(LatLng latLng) { }
}
