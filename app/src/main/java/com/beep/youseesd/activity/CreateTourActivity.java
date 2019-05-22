package com.beep.youseesd.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beep.youseesd.R;
import com.beep.youseesd.adapter.CreateTourLocationAdapter;
import com.beep.youseesd.model.TourLocation;
import com.google.android.material.chip.ChipGroup;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.pedromassango.ibackdrop.Backdrop;

import java.util.ArrayList;
import java.util.List;

public class CreateTourActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Backdrop backdrop;

    private RecyclerView locationList;
    private LinearLayoutManager layoutManager;
    private CreateTourLocationAdapter adapter;
    private TextView locationsFrontTitle;

    private ChipGroup collegeGroup, majorGroup;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backdrop.closeBackdrop();
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);
        toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Create Tour");
        setSupportActionBar(toolbar);

        locationList = (RecyclerView) findViewById(R.id.list_location);
        backdrop = (Backdrop) findViewById(R.id.backdrop_view);
        locationsFrontTitle = (TextView) findViewById(R.id.locations_front_title);

        layoutManager = new LinearLayoutManager(this);
        locationList.setLayoutManager(layoutManager);

        List<TourLocation> tourLocations = createLocations();
        adapter = new CreateTourLocationAdapter(tourLocations);
        locationList.setAdapter(adapter);

        locationsFrontTitle.setText(tourLocations.size() + " Locations Added");
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
//        locationList.addItemDecoration(dividerItemDecoration);

        collegeGroup = (ChipGroup) findViewById(R.id.college_group);
        majorGroup = (ChipGroup) findViewById(R.id.major_group);
        for (int i = 0; i < majorGroup.getChildCount(); i++) {
            majorGroup.getChildAt(i).setOnClickListener(v -> backdrop.closeBackdrop());
        }

        for (int i = 0; i < collegeGroup.getChildCount(); i++) {
            collegeGroup.getChildAt(i).setOnClickListener(v -> backdrop.closeBackdrop());
        }
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
