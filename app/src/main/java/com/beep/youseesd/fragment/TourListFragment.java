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
import com.beep.youseesd.application.App;
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

/**
 * Fragment that lists the tours on the home page
 */
public class TourListFragment extends Fragment {

  private RecyclerView tourListView;
  private LinearLayoutManager layoutManager;
  private HomeTourAdapter adapter;
  private ProgressBar progressBar;
  private ImageView mEmptyImageView;

  /**
   * Lifecycle method that will inflate our fragment layout
   *
   * @param inflater the inflater that will be used to inflate our layout
   * @param container the container that our layout be contained within
   * @param savedInstanceState state of our app
   * @return the view that was loaded
   */
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  /**
   * Lifecycle method that handles the displays the tours onto the view
   *
   * @param view the view we're placing our tours on
   * @param savedInstanceState used in the super call
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // link UI elements
    mEmptyImageView = view.findViewById(R.id.home_empty_image);
    progressBar = view.findViewById(R.id.home_progressbar);
    tourListView = view.findViewById(R.id.list_tour);

    // if the tour is empty display a background image so it's not just an empty page
    Glide.with(getActivity())
        .load(getResources().getDrawable(R.drawable.tour_empty))
        .apply(RequestOptions.circleCropTransform())
        .into(mEmptyImageView);

    // link the adapter and layoutManager and load the tours using the user's id
    adapter = new HomeTourAdapter((HomeActivity) getActivity(), new ArrayList<>());
    layoutManager = new LinearLayoutManager(view.getContext());
    tourListView.setLayoutManager(layoutManager);
    loadTours(App.getUser().getUid());
  }

  /**
   * Helper method that loads the tours under a user's id from database
   *
   * @param uid the user's id
   */
  private void loadTours(String uid) {
    DatabaseUtil.getTourDatabase(uid).addValueEventListener(new ValueEventListener() {
      /**
       * Get the tours underneath a user and store them into a list for use
       *
       * @param dataSnapshot all of the tours under a user's account
       */
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<Tour> tours = new ArrayList<>();
        // go through the children and parse each element as a Tour object
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
          String dsid = ds.getKey();
          Tour t = ds.getValue(Tour.class);
          t.tourId = dsid;
          tours.add(t);
        }

        // using the retrieved data, pass through the adapter to force an update
        adapter.updateTours(tours);
        tourListView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        mEmptyImageView.setVisibility(tours.isEmpty() ? View.VISIBLE : View.GONE);
      }

      /**
       * Handles the error when our tours weren't loaded properly
       *
       * @param databaseError object containing information regarding the error that occurred
       */
      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
        WLog.e(databaseError.getMessage());
        progressBar.setVisibility(View.GONE);
        mEmptyImageView.setVisibility(View.VISIBLE);
        Snackbar.make(getView(), "Something went wrong. Failed to load tours", Snackbar.LENGTH_LONG)
            .show();
      }
    });
  }
}
