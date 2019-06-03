package com.beep.youseesd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.beep.youseesd.R;
import com.beep.youseesd.activity.OnTourActivity;
import com.beep.youseesd.model.Location;
import com.beep.youseesd.model.Tour;
import com.beep.youseesd.model.TourSet;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Adapter for the locations to visit on the tour
 */
public class TourLocationManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int TOUR_LOCATION_VIEW = 0x01;
  private static final int TOUR_END_FOOTER_VIEW = 0x02;

  private OnTourActivity mActivity;
  private List<Location> mStops;

  /**
   * Constructor for the adapter. Stores all of the necessary information for our view
   *
   * @param act a reference to the OnTourActivity in which we will be displaying stuff on
   * @param tour a reference to the Tour we're currently on
   */
  public TourLocationManageAdapter(OnTourActivity act, Tour tour) {
    super();

    // save all of the locations on tour
    mStops = new ArrayList<>();
    for (String location : tour.locations) {
      mStops.add(TourSet.allLocations.get(location));
    }

    this.mActivity = act;
  }

  /**
   * Determines the view that we're handling
   *
   * @param position the position of the view
   * @return the corresponding int of the view with respect to position
   */
  @Override
  public int getItemViewType(int position) {
    if (position == mStops.size()) {
      return TOUR_END_FOOTER_VIEW;
    }

    return TOUR_LOCATION_VIEW;
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
    switch (viewType) {
      case TOUR_LOCATION_VIEW:
        View v = inflater.inflate(R.layout.item_tour_location, parent, false);
        return new TourLocationManageViewHolder(v);
      case TOUR_END_FOOTER_VIEW:
        View footerView = inflater.inflate(R.layout.item_tour_end_footer, parent, false);
        return new EndTourFooterViewHolder(footerView);
    }
    return null;
  }

  /**
   * Lifecycle method that gets called once our ViewHolder has been properly binded
   *
   * @param holder the holder that we're dealing with
   * @param i the position of our view
   */
  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
    if (holder instanceof TourLocationManageViewHolder) {
      TourLocationManageViewHolder h = (TourLocationManageViewHolder) holder;
      Location l = mStops.get(i);
      if (l == null) {
        return;
      }

      // set listeners to each of the locations on the tour so we can mark as (un)visited
      h.mLayout.setOnClickListener(v -> {
        if (!l.isVisited()) {
          l.setVisited();
        } else {
          l.setUnvisited();
        }
        notifyDataSetChanged();
        mActivity.updateLocationPinMarkerVisited(l.isVisited(), i);
        mActivity.updateBottomSheetCollapsed(mStops.get(i));
      });

      // set the appropriate text on each of the locations in the menu depending if visited
      h.mTitleView.setText(l.title);
      if (l.isVisited()) {
        long time = Calendar.getInstance().getTimeInMillis() - l.getVisitedTime();
        if (time == 1) {
          h.mSubtitleView.setText("just visited!");
        } else {
          h.mSubtitleView.setText("visited " + time + "ago");
        }
        h.mSubtitleView.setVisibility(View.VISIBLE);
        h.mCheckImageView.setIcon(new IconicsDrawable(mActivity)
            .icon(MaterialDesignIconic.Icon.gmi_check_square)
            .color(mActivity.getResources().getColor(R.color.primaryColor))
            .sizeDp(16));
      } else {
        h.mSubtitleView.setVisibility(View.GONE);
        h.mCheckImageView.setIcon(new IconicsDrawable(mActivity)
            .icon(MaterialDesignIconic.Icon.gmi_check_square)
            .color(mActivity.getResources().getColor(R.color.light_gray))
            .sizeDp(16));
      }
    } else if (holder instanceof EndTourFooterViewHolder) {
      // add a listener to our endTour button
      EndTourFooterViewHolder h = (EndTourFooterViewHolder) holder;
      h.mEndTourButton.setOnClickListener(mActivity);
    }
  }

  /**
   * Returns the number of locations on our tour + 1
   *
   * @return the number of locations on our tour + 1
   */
  @Override
  public int getItemCount() {
    return mStops.size() + 1;
  }

  /**
   * Holder for the TourLocations menu
   */
  static class TourLocationManageViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitleView;
    private IconicsImageView mCheckImageView;
    private TextView mSubtitleView;
    private LinearLayout mLayout;

    /**
     * Constructor for the TourLocations holder
     *
     * @param itemView the view we have a reference to in our holder
     */
    public TourLocationManageViewHolder(@NonNull View itemView) {
      super(itemView);

      mLayout = itemView.findViewById(R.id.item_tour_location_layout);
      mTitleView = itemView.findViewById(R.id.item_tour_location_title);
      mSubtitleView = itemView.findViewById(R.id.item_tour_location_subtitle);
      mCheckImageView = itemView.findViewById(R.id.item_tour_location_checkview);
    }
  }

  /**
   * Holder for the EndTourFooter
   */
  static class EndTourFooterViewHolder extends RecyclerView.ViewHolder {

    private View mEndTourButton;

    /**
     * Constructor for the EndTourFooter holder
     *
     * @param itemView the view we have a reference to in our holder
     */
    EndTourFooterViewHolder(@NonNull View itemView) {
      super(itemView);
      mEndTourButton = itemView.findViewById(R.id.item_end_tour_btn);
    }
  }
}
