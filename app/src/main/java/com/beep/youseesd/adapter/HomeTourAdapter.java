package com.beep.youseesd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beep.youseesd.R;
import com.beep.youseesd.model.Tour;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class HomeTourAdapter extends RecyclerView.Adapter<HomeTourAdapter.HomeTourViewHolder> {

    private List<Tour> mTours;

    public HomeTourAdapter(List<Tour> tours) {
        super();
        this.mTours = tours;
    }

    @NonNull
    @Override
    public HomeTourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tour, parent, false);
        return new HomeTourViewHolder(v);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTourViewHolder holder, int i) {
        holder.titleView.setText(mTours.get(i).getTitle());
//        holder.personAge.setText(persons.get(i).age);
//        holder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return mTours.size();
    }

    static class HomeTourViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView cardView;
        private TextView titleView;

        public HomeTourViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.card_tour_title);
        }
    }
}
