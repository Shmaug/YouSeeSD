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
import com.beep.youseesd.application.App;
import com.beep.youseesd.model.Tour;
import com.beep.youseesd.util.DatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Fragment that handles our confirmation screen before starting tour
 */
public class ConfirmOnTourFragment extends Fragment {

  private ConfirmLocationAdapter adapter;
  private RecyclerView confirmTourListView;
  private LinearLayoutManager layoutManager;

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
    return inflater.inflate(R.layout.fragment_confirm_tour, container, false);
  }

  /**
   * Lifecycle method that calls on the super method
   *
   * @param savedInstanceState used for the super call
   */
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  /**
   * Lifecycle method that links UI elements and loads the tours
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // link UI elements and set the layoutManager
    confirmTourListView = view.findViewById(R.id.list_confirm_location);
    layoutManager = new LinearLayoutManager(view.getContext());
    confirmTourListView.setLayoutManager(layoutManager);

    // get the current user's id and load the tour in question
    String uid = App.getUser().getUid();
    String tourId = getArguments().getString(DatabaseUtil.getTourId());
    loadLocations(uid, tourId);
  }

  /**
   * Helper method that will update our UI based on the tour passed in
   *
   * @param t the tour that the user selected
   */
  private void updateUI(Tour t) {
    // use our adapter to display information on the locations in our tour
    adapter = new ConfirmLocationAdapter();
    confirmTourListView.setAdapter(adapter);
    adapter.updateData(t);
  }

  /**
   * Helper method that loads the locations within our tour by requesting data from database
   *
   * @param uid the id of the user
   * @param tourId the id of the tour under the user's account
   */
  private void loadLocations(String uid, String tourId) {
    // get a reference to our database
    DatabaseReference tourRef = DatabaseUtil.getSingleTourDatabase(uid, tourId);
    tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
      /**
       * Get the tour in question from our database and call on the helper method
       *
       * @param dataSnapshot the data that we are attempting to retrieve
       */
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // try to parse the data as a Tour object
        Tour t = dataSnapshot.getValue(Tour.class);
        if (t == null) {
          return;
        }

        updateUI(t);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }
}
