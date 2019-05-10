package com.beep.youseesd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beep.youseesd.R;
import com.beep.youseesd.model.Tour;
import com.beep.youseesd.util.WLog;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class HomeTourAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TOUR_VIEW = 0x01;
    private static final int FOOTER_VIEW = 0x02;

    private List<Tour> mTours;

    public HomeTourAdapter(List<Tour> tours) {
        super();
        this.mTours = tours;
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= mTours.size()) {
            return FOOTER_VIEW;
        }

        return TOUR_VIEW;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        WLog.i("viewType: " + viewType);
        if (viewType == FOOTER_VIEW) {
            View footerView = inflater.inflate(R.layout.item_footer, parent, false);
            return new FooterViewHolder(footerView);
        }

        View v = inflater.inflate(R.layout.item_tour, parent, false);
        return new HomeTourViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HomeTourViewHolder) {
            HomeTourViewHolder h = (HomeTourViewHolder) holder;
            h.titleView.setText(mTours.get(i).getTitle());
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder h = (FooterViewHolder) holder;
            WLog.i("footer holder called");

        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mTours.size() + 1;
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
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
