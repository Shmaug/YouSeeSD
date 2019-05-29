package com.beep.youseesd.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.beep.youseesd.R;
import com.beep.youseesd.model.TourLocation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DatabaseReference;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import java.util.ArrayList;
import java.util.List;

/**
 * The screen for the "Create Tour" part of our app
 */
public class CreateTourActivity extends AppCompatActivity {

  private MaterialToolbar mToolbar;
  private DatabaseReference mDatabase;
  private TextView locationsFrontTitle;

  private ChipGroup collegeGroup, majorGroup;

  /**
   * Closes the backdrop when the back button is pressed
   */
  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_create_tour, menu);
    menu.findItem(R.id.menu_create_tour_ok).setIcon(
        new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_check)
            .actionBar().color(Color.WHITE)
    );
    return true;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_tour);
    mToolbar = (MaterialToolbar) findViewById(R.id.create_toolbar);
    setSupportActionBar(mToolbar);

    List<TourLocation> tourLocations = createLocations();
    collegeGroup = (ChipGroup) findViewById(R.id.college_group);
    majorGroup = (ChipGroup) findViewById(R.id.major_group);
  }

  private List<TourLocation> createLocations() {
    List<TourLocation> tourLocations = new ArrayList<>();
    tourLocations.add(new TourLocation("Geisel Library", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/geisel.jpg"));
    tourLocations.add(new TourLocation("Medical Education and Telemedicine building", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/Medical-Education-Telemedicine-Building.jpg"));
    tourLocations.add(new TourLocation("Rady School of Management", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/Rady-School-of-Management.jpg"));
    tourLocations.add(new TourLocation("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"));
    tourLocations.add(new TourLocation("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"));
    tourLocations.add(new TourLocation("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"));
    tourLocations.add(new TourLocation("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"));
    tourLocations.add(new TourLocation("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"));
    return tourLocations;
  }
}
