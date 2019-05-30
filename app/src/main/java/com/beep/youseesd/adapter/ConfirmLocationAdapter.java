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
import com.beep.youseesd.model.Tour;
import com.bumptech.glide.Glide;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.mikepenz.iconics.view.IconicsTextView;
import java.util.List;

public class ConfirmLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int TRANSPARENT_HEADER_VIEW = 0x01;
  private static final int LOCATION_VIEW = 0x02;
  private static final int FOOTER_VIEW = 0x03;

  private Tour mTour;
  private List<Location> tourLocations;

  public ConfirmLocationAdapter() {
  }

  public void updateData(Tour t) {
    this.mTour = t;
    this.tourLocations = t.locations;
    notifyDataSetChanged();
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return TRANSPARENT_HEADER_VIEW;
    } else if (position >= tourLocations.size()) {
      return FOOTER_VIEW;
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
    } else if (viewType == FOOTER_VIEW) {
      View footerView = inflater.inflate(R.layout.item_footer, parent, false);
      return new FooterViewHolder(footerView);
    }

    View v = inflater.inflate(R.layout.item_location, parent, false);
    return new LocationViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
    if (holder instanceof LocationViewHolder) {
      LocationViewHolder h = (LocationViewHolder) holder;
      h.title.setText(tourLocations.get(i).title);
      h.subtitle.setText(tourLocations.get(i).subtitle);
      Glide.with(h.imageView.getContext())
          .load(tourLocations.get(i).imageUrl)
          .centerCrop()
          .into(h.imageView);
    } else if (holder instanceof HeaderViewHolder) {
      HeaderViewHolder h = (HeaderViewHolder) holder;
      Glide.with(h.imageView.getContext())
          .load(mTour.imageUrl)
          .centerCrop()
          .into(h.imageView);

      h.titleView.setText("Are you ready to enjoy " + mTour.estimatedTime + " mins?");
      h.titleView.setDrawableEnd(new IconicsDrawable(h.titleView.getContext()).icon(FontAwesome.Icon.faw_smile).color(Color.GRAY).paddingDp(3).sizeDp(24));
    } else if (holder instanceof FooterViewHolder) {
      FooterViewHolder h = (FooterViewHolder) holder;
    }
  }

  @Override
  public int getItemCount() {
    return tourLocations.size() + 1;
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
    private IconicsTextView titleView;

    public HeaderViewHolder(@NonNull View itemView) {
      super(itemView);
      titleView = (IconicsTextView) itemView.findViewById(R.id.confirm_locations_title);

      imageView = (IconicsImageView) itemView.findViewById(R.id.card_location_header);
      imageView.setIcon(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_image).color(Color.GRAY).sizeDp(24));
    }
  }

  static class FooterViewHolder extends RecyclerView.ViewHolder {

    public FooterViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
