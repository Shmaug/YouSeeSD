package com.beep.youseesd.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beep.youseesd.R;
import com.beep.youseesd.adapter.CreateTourLocationAdapter;
import com.beep.youseesd.model.Location;
import com.google.android.material.chip.ChipGroup;
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
        getMenuInflater().inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);
        toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("");
        locationList = (RecyclerView) findViewById(R.id.list_location);
        backdrop = (Backdrop) findViewById(R.id.backdrop_view);
        locationsFrontTitle = (TextView) findViewById(R.id.locations_front_title);

        layoutManager = new LinearLayoutManager(this);
        locationList.setLayoutManager(layoutManager);

        List<Location> locations = createLocations();
        adapter = new CreateTourLocationAdapter(locations);
        locationList.setAdapter(adapter);

        locationsFrontTitle.setText(locations.size() + " Locations Added");
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        locationList.addItemDecoration(dividerItemDecoration);

        collegeGroup = (ChipGroup) findViewById(R.id.college_group);
        majorGroup = (ChipGroup) findViewById(R.id.major_group);
        for (int i = 0; i < majorGroup.getChildCount(); i++) {
            majorGroup.getChildAt(i).setOnClickListener(v -> backdrop.closeBackdrop());
        }

        for (int i = 0; i < collegeGroup.getChildCount(); i++) {
            collegeGroup.getChildAt(i).setOnClickListener(v -> backdrop.closeBackdrop());
        }
    }

    private List<Location> createLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Geisel Library", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/geisel.jpg"));
        locations.add(new Location("Medical Education and Telemedicine building", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/Medical-Education-Telemedicine-Building.jpg"));
        locations.add(new Location("Rady School of Management", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/Rady-School-of-Management.jpg"));
        locations.add(new Location("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"));
        locations.add(new Location("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"));
        locations.add(new Location("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"));
        locations.add(new Location("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"));
        locations.add(new Location("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"));
        return locations;
    }
}
