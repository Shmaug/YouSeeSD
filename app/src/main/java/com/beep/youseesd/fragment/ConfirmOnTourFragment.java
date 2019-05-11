package com.beep.youseesd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beep.youseesd.R;
import com.beep.youseesd.adapter.ConfirmLocationAdapter;
import com.beep.youseesd.model.Location;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOnTourFragment extends Fragment {

    private ConfirmLocationAdapter adapter;
    private RecyclerView confirmTourListView;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_tour, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        confirmTourListView = (RecyclerView) view.findViewById(R.id.list_confirm_location);
        layoutManager = new LinearLayoutManager(view.getContext());
        confirmTourListView.setLayoutManager(layoutManager);
        adapter = new ConfirmLocationAdapter(createLocations());
        confirmTourListView.setAdapter(adapter);
    }

    private List<Location> createLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Geisel Library", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/geisel.jpg"));
        locations.add(new Location("Medical Education and Telemedicine building", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/Medical-Education-Telemedicine-Building.jpg"));
        locations.add(new Location("Rady School of Management", "The best spot at UCSD", "https://ucpa.ucsd.edu/images/image_library/Rady-School-of-Management.jpg"));
        locations.add(new Location("Price Center West", "The heart of UCSD", "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"));
        locations.add(new Location("York Hall", "The largest lecture hall", "https://ucpa.ucsd.edu/images/image_library/York-Hall.jpg"));
        return locations;
    }
}
