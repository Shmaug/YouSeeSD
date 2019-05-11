package com.beep.youseesd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beep.youseesd.R;
import com.beep.youseesd.adapter.HomeTourAdapter;
import com.beep.youseesd.model.Tour;

import java.util.ArrayList;
import java.util.List;

public class TourListFragment extends Fragment {

    private RecyclerView tourListView;
    private LinearLayoutManager layoutManager;
    private HomeTourAdapter adapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) view.findViewById(R.id.home_progressbar);
        tourListView = (RecyclerView) view.findViewById(R.id.list_tour);

        layoutManager = new LinearLayoutManager(view.getContext());
        tourListView.setLayoutManager(layoutManager);

        adapter = new HomeTourAdapter(createTours());

        tourListView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        tourListView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        progressBar.setVisibility(View.GONE);
                    }
                });

        tourListView.setAdapter(adapter);
    }

    private List<Tour> createTours() {
        List<Tour> tours = new ArrayList<>();
        tours.add(new Tour("Good to Walk").subTitle("Muir is the first college founded at UCSD").imageUrl("https://lh5.googleusercontent.com/p/AF1QipOa9szj6hbfUFULO6IuTFzDua8-5FAIxkmjQkTu=s500-k-no"));
        tours.add(new Tour("Congratulations!").subTitle("7 spots must be visited for engineering students").imageUrl("https://cse.ucsd.edu/sites/cse.ucsd.edu/files/2018-07/Front-Entrance-at-Dusk-1.jpg"));
        tours.add(new Tour("Bring your puppy!").subTitle("Get some fresh air at UCSD").imageUrl("https://ucpa.ucsd.edu/images/image_library/fallen-star-and-jsoe-building.jpg"));
        tours.add(new Tour("Nice Spots for Sunset").subTitle("Time to take a relax").imageUrl("https://ucpa.ucsd.edu/images/image_library/geisel.jpg"));
        tours.add(new Tour("more item").imageUrl("http://i.imgur.com/DvpvklR.png"));
        tours.add(new Tour("cse110").imageUrl("http://i.imgur.com/DvpvklR.png"));
        tours.add(new Tour("Gary").imageUrl("http://i.imgur.com/DvpvklR.png"));
        tours.add(new Tour("we just did quiz3").imageUrl("http://i.imgur.com/DvpvklR.png"));
        return tours;
    }
}
