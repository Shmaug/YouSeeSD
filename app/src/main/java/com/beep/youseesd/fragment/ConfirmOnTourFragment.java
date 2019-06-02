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

    String uid = App.getUser().getUid();
    String tourId = getArguments().getString(DatabaseUtil.TOUR_ID);
    loadLocations(uid, tourId);
  }

  private void updateUI(Tour t) {
    adapter = new ConfirmLocationAdapter();
    confirmTourListView.setAdapter(adapter);
    adapter.updateData(t);
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

        updateUI(t);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }
}
