package com.beep.youseesd.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beep.youseesd.R;
import com.beep.youseesd.model.Location;
import com.bumptech.glide.Glide;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;

import java.util.List;

public class ConfirmLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TRANSPARENT_HEADER_VIEW = 0x01;
    private static final int LOCATION_VIEW = 0x02;

    private List<Location> locations;

    public ConfirmLocationAdapter(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TRANSPARENT_HEADER_VIEW;
        }

        return LOCATION_VIEW;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TRANSPARENT_HEADER_VIEW) {
            View headerView = inflater.inflate(R.layout.item_transparent_header, parent, false);
            return new HeaderViewHolder(headerView);
        }

        View v = inflater.inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof LocationViewHolder) {
            LocationViewHolder h = (LocationViewHolder) holder;
            h.title.setText(locations.get(i).title);
            h.subtitle.setText(locations.get(i).subtitle);
            Glide.with(h.imageView.getContext())
                    .load(locations.get(i).imageUrl)
                    .centerCrop()
                    .into(h.imageView);
        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder h = (HeaderViewHolder) holder;
            Glide.with(h.imageView.getContext())
                    .load("https://ucpa.ucsd.edu/images/image_library/geisel.jpg")
                    .centerCrop()
                    .into(h.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {

        private IconicsImageView imageView;
        private TextView title, subtitle;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.location_card_title);
            subtitle = (TextView) itemView.findViewById(R.id.location_card_subtitle);

            imageView = (IconicsImageView) itemView.findViewById(R.id.card_location_img);
            imageView.setIcon(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_image).color(Color.GRAY).sizeDp(24));
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private IconicsImageView imageView;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (IconicsImageView) itemView.findViewById(R.id.card_location_header);
            imageView.setIcon(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_image).color(Color.GRAY).sizeDp(24));
        }
    }
}
