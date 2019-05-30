package com.beep.youseesd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.beep.youseesd.R;
import com.beep.youseesd.activity.HomeActivity;
import com.beep.youseesd.adapter.HomeTourAdapter;
import com.beep.youseesd.model.Tour;
import com.beep.youseesd.util.DatabaseUtil;
import com.beep.youseesd.util.WLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class TourListFragment extends Fragment {

  private RecyclerView tourListView;
  private LinearLayoutManager layoutManager;
  private HomeTourAdapter adapter;
  private ProgressBar progressBar;
  private ImageView mEmptyImageView;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mEmptyImageView = (ImageView) view.findViewById(R.id.home_empty_image);
    progressBar = (ProgressBar) view.findViewById(R.id.home_progressbar);
    tourListView = (RecyclerView) view.findViewById(R.id.list_tour);

    Glide.with(getActivity())
        .load(getResources().getDrawable(R.drawable.tour_empty))
        .apply(RequestOptions.circleCropTransform())
        .into(mEmptyImageView);

    adapter = new HomeTourAdapter((HomeActivity) getActivity(), new ArrayList<>());
    layoutManager = new LinearLayoutManager(view.getContext());
    tourListView.setLayoutManager(layoutManager);
    loadTours("jDbXXUNhVuSHE3y8tEbNNVZfzpJ3");
//    loadTours(App.getUser().getUid());
  }

  private void loadTours(String uid) {
    DatabaseUtil.getTourDatabase(uid).addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<Tour> tours = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
          String dsid = ds.getKey();
          Tour t = ds.getValue(Tour.class);
          t.tourId = dsid;
          tours.add(t);
        }

        adapter.updateTours(tours);
        tourListView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        mEmptyImageView.setVisibility(tours.isEmpty() ? View.VISIBLE : View.GONE);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
        WLog.e(databaseError.getMessage());
        progressBar.setVisibility(View.GONE);
        mEmptyImageView.setVisibility(View.VISIBLE);
        Snackbar.make(getView(), "Something went wrong. Failed to load tours", Snackbar.LENGTH_LONG).show();
      }
    });
  }
}
