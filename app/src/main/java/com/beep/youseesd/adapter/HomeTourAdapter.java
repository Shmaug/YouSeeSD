package com.beep.youseesd.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beep.youseesd.R;
import com.beep.youseesd.model.Tour;
import com.beep.youseesd.util.WLog;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.mikepenz.iconics.view.IconicsTextView;

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
            h.subtitleView.setText(mTours.get(i).getSubtitle());
            Glide.with(h.imageView.getContext())
                    .load(mTours.get(i).getImageUrl())
                    .centerCrop()
                    .into(h.imageView);

            h.cardView.setOnClickListener(v -> {

            });

            h.menuImageView.setOnClickListener(v -> {

            });

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
        private TextView subtitleView;
        private IconicsImageView imageView;
        private IconicsTextView hashTextView1;
        private IconicsTextView hashTextView2;
        private IconicsImageView menuImageView;

        public HomeTourViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView.findViewById(R.id.card_tour);

            titleView = (TextView) itemView.findViewById(R.id.card_tour_title);
            subtitleView = (TextView) itemView.findViewById(R.id.card_tour_subtitle);

            imageView = (IconicsImageView) itemView.findViewById(R.id.card_tour_img);
            imageView.setIcon(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_image).color(Color.GRAY).sizeDp(24));

            hashTextView1 = (IconicsTextView) itemView.findViewById(R.id.card_tour_hash1);
            hashTextView1.setDrawableStart(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_hashtag).color(hashTextView1.getResources().getColor(R.color.gray)).sizeDp(12));

            hashTextView2 = (IconicsTextView) itemView.findViewById(R.id.card_tour_hash2);
            hashTextView2.setDrawableStart(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_hashtag).color(hashTextView2.getResources().getColor(R.color.gray)).sizeDp(12));

            menuImageView = (IconicsImageView) itemView.findViewById(R.id.card_tour_menu);
            menuImageView.setIcon(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_ellipsis_v).color(Color.LTGRAY).sizeDp(14));

        }
    }
}