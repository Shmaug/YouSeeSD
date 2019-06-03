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
import com.beep.youseesd.model.TourSet;
import com.bumptech.glide.Glide;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.mikepenz.iconics.view.IconicsTextView;
import java.util.List;

/**
 * Adapter for the screen we display before starting the tour
 */
public class ConfirmLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int TRANSPARENT_HEADER_VIEW = 0x01;
  private static final int LOCATION_VIEW = 0x02;
  private static final int FOOTER_VIEW = 0x03;

  private Tour mTour;
  private List<String> tourLocations;

  public ConfirmLocationAdapter() {
  }

  /**
   * Updates the data of tour locations using t
   *
   * @param t the tour we're on
   */
  public void updateData(Tour t) {
    this.mTour = t;
    this.tourLocations = t.getLocations();
    // this will force an update on the UI
    notifyDataSetChanged();
  }

  /**
   * Determine what kind of view we should be in
   *
   * @param position the position that we're at
   * @return the corresponding view with respect to position
   */
  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return TRANSPARENT_HEADER_VIEW;
    } else if (position > tourLocations.size()) {
      return FOOTER_VIEW;
    }

    return LOCATION_VIEW;
  }

  /**
   * Creates the holder for our view, setting up the three views
   *
   * @param parent the parent that we're going to display our views within
   * @param viewType the type of view we're dealing with
   * @return a ViewHolder with the view within it
   */
  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    // three different cases depending on the viewType
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

  /**
   * Lifecycle method that gets called once our ViewHolder has been properly binded
   *
   * @param holder the holder that we're dealing with
   * @param i the position of our view
   */
  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
    if (holder instanceof LocationViewHolder) {
      // discount the position by one to account for all of the locations on our tour
      i--;
      // typecast the holder to a more specific one
      LocationViewHolder h = (LocationViewHolder) holder;

      // display the information as necessary
      Location loc = TourSet.getAllLocations().get(tourLocations.get(i));
      h.title.setText(loc.getTitle());
      h.subtitle.setText(loc.getSubtitle());
      Glide.with(h.imageView.getContext())
          .load(loc.getImageUrl())
          .centerCrop()
          .into(h.imageView);
    } else if (holder instanceof HeaderViewHolder) {
      // display the appropriate information
      HeaderViewHolder h = (HeaderViewHolder) holder;
      Glide.with(h.imageView.getContext())
          .load(mTour.getImageUrl())
          .centerCrop()
          .into(h.imageView);

      // display the text for the tours
      h.titleView.setText("Are you ready to enjoy " + mTour.getEstimatedTime() + " mins? ");
      h.titleView.setDrawableEnd(
          new IconicsDrawable(h.titleView.getContext()).icon(FontAwesome.Icon.faw_smile)
              .color(Color.GRAY)
              .paddingDp(3)
              .sizeDp(24));
    }
    // we don't need to do anything with the FooterViewHolder
  }

  /**
   * Getter for the size of the tour
   *
   * @return the number of stops in the tour + 1
   */
  @Override
  public int getItemCount() {
    return tourLocations.size() + 1;
  }

  /**
   * Holder for each location
   */
  static class LocationViewHolder extends RecyclerView.ViewHolder {

    private IconicsImageView imageView;
    private TextView title, subtitle;

    /**
     * Constructor for the location holder
     *
     * @param itemView the view we have a reference to in our holder
     */
    public LocationViewHolder(@NonNull View itemView) {
      super(itemView);

      title = itemView.findViewById(R.id.location_card_title);
      subtitle = itemView.findViewById(R.id.location_card_subtitle);

      imageView = itemView.findViewById(R.id.card_location_img);
      imageView.setIcon(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_image)
          .color(Color.GRAY)
          .sizeDp(24));
    }
  }

  /**
   * Holder for the header
   */
  static class HeaderViewHolder extends RecyclerView.ViewHolder {

    private IconicsImageView imageView;
    private IconicsTextView titleView;

    /**
     * Constructor for the header holder
     *
     * @param itemView the view we have a reference to in our holder
     */
    public HeaderViewHolder(@NonNull View itemView) {
      super(itemView);

      titleView = itemView.findViewById(R.id.confirm_locations_title);

      imageView = itemView.findViewById(R.id.card_location_header);
      imageView.setIcon(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_image)
          .color(Color.GRAY)
          .sizeDp(24));
    }
  }

  /**
   * Holder for the footer
   */
  static class FooterViewHolder extends RecyclerView.ViewHolder {

    /**
     * Constructor for the footer holder
     *
     * @param itemView the view we have a reference to in our holder
     */
    public FooterViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
