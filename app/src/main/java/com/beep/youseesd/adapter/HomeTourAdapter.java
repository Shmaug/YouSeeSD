package com.beep.youseesd.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.beep.youseesd.R;
import com.beep.youseesd.activity.HomeActivity;
import com.beep.youseesd.activity.OnTourActivity;
import com.beep.youseesd.application.App;
import com.beep.youseesd.fragment.ConfirmOnTourFragment;
import com.beep.youseesd.model.Tour;
import com.beep.youseesd.util.DatabaseUtil;
import com.beep.youseesd.view.TourTagTextView;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.card.MaterialCardView;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.mikepenz.iconics.view.IconicsTextView;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.nex3z.flowlayout.FlowLayout;
import java.util.Collections;
import java.util.List;

/**
 * Adapter for the home page
 */
public class HomeTourAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int TOUR_VIEW = 0x01;
  private static final int FOOTER_VIEW = 0x02;

  private List<Tour> mTours;
  private HomeActivity act;

  /**
   * Constructor that sets up the tours that we have to display
   *
   * @param act reference to the home activity
   * @param tours list of tours given to us from the database
   */
  public HomeTourAdapter(HomeActivity act, List<Tour> tours) {
    super();

    // reverse tours so it's intuitive for user
    Collections.reverse(tours);
    this.mTours = tours;
    this.act = act;
  }

  /**
   * Updates the tour list with an updated one from the database
   *
   * @param tours list of tours to update with
   */
  public void updateTours(List<Tour> tours) {
    // reverse tours so it's intuitive for user
    Collections.reverse(tours);

    this.mTours = tours;
    notifyDataSetChanged();
  }

  /**
   * Determines what view we're in based on position
   *
   * @param position the position we're at
   * @return the corresponding int that represents the view
   */
  @Override
  public int getItemViewType(int position) {
    if (position >= mTours.size()) {
      return FOOTER_VIEW;
    }

    return TOUR_VIEW;
  }

  /**
   * Lifecycle method that handles the creation of our holders
   *
   * @param parent where our holders will be created within
   * @param viewType the type of view that we're dealing with
   * @return the ViewHolder that we want to use based on viewType
   */
  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    if (viewType == FOOTER_VIEW) {
      View footerView = inflater.inflate(R.layout.item_footer, parent, false);
      return new FooterViewHolder(footerView);
    }

    View v = inflater.inflate(R.layout.item_tour2, parent, false);
    return new HomeTourViewHolder(v);
  }

  /**
   * Lifecycle method that's called once our holders are properly binded
   *
   * @param holder the holder we're dealing with
   * @param i the int of the tour we're currently handling
   */
  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
    if (holder instanceof HomeTourViewHolder) {
      // typecast the holder
      HomeTourViewHolder h = (HomeTourViewHolder) holder;

      // get the tour with respect to i and properly load the information
      Tour t = mTours.get(i);
      h.titleView.setText(mTours.get(i).getTitle());
      h.subtitleView.setText(mTours.get(i).getSubtitle());
      Glide.with(h.imageView.getContext())
          .load(mTours.get(i).getImageUrl())
          .centerCrop()
          .into(h.imageView);
      h.tourTravelTimeView.setText(mTours.get(i).getEstimatedTime() + " mins");

      // modify the layout to display the tags if available
      for (String tagLabel : t.getSelectedTags()) {
        TourTagTextView tagTextView = new TourTagTextView(h.tourTagsLayout.getContext(), tagLabel);
        h.tourTagsLayout.addView(tagTextView);
      }
      h.tourTagTitle.setText(t.getSelectedTags().isEmpty() ? "Show Tour Details" : "Tour Tags");

      // add a listener to the card so that we can prepare to start a new tour
      h.cardView.setOnClickListener(v -> {
        switch (act.getAppBar().getFabAlignmentMode()) {
          case BottomAppBar.FAB_ALIGNMENT_MODE_CENTER:
            act.getAppBar().setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
            act.getAppBar().setFabAnimationMode(BottomAppBar.FAB_ANIMATION_MODE_SCALE);
            act.getWeatherTextView().setVisibility(View.GONE);

            // change the icon to show a different icon
            act.getFAB()
                .setImageDrawable(new IconicsDrawable(act).icon(MaterialDesignIconic.Icon.gmi_play)
                    .color(Color.WHITE)
                    .sizeDp(24));

            // load the ConfirmOnTourFragment that will display the locations on our tour
            ConfirmOnTourFragment fragment = new ConfirmOnTourFragment();
            Bundle b = new Bundle();
            b.putString(DatabaseUtil.getTourId(), t.getTourId());
            fragment.setArguments(b);
            act.updateFragment(fragment, "ConfirmOnTour");

            // add a listener to the button that will start the tour if clicked on
            act.getFAB().setOnClickListener(v1 -> {
              Intent intent = new Intent(v1.getContext(), OnTourActivity.class);
              intent.putExtra(DatabaseUtil.getTourId(), t.getTourId());
              act.startActivity(intent);
            });
            break;
        }
      });

      // add a listener to the three dots on each card so the user is allowed to delete a tour
      h.menuImageView.setOnClickListener(v -> {
        PopupMenu popup = new PopupMenu(act, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_tour_card, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(item -> {
          switch (item.getItemId()) {
            case R.id.delete_tour:
              DatabaseUtil.deleteTour(App.getUser().getUid(), t.getTourId());
              return true;
          }

          return false;
        });
      });
    }
    // we don't handle the FooterViewHolder since we don't need to do anything with it
  }

  /**
   * Lifecycle method that gets called once we have properly attached the View
   *
   * @param recyclerView the view to be used with the super method
   */
  @Override
  public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
  }

  /**
   * Returns the number of tours we have + 1 for the view
   *
   * @return the number of tours we have + 1
   */
  @Override
  public int getItemCount() {
    return mTours.size() + 1;
  }

  /**
   * Holder for the footer
   */
  static class FooterViewHolder extends RecyclerView.ViewHolder {

    /**
     * Constructor for the header holder
     *
     * @param itemView the view we have a reference to in our holder
     */
    public FooterViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }

  /**
   * Holder for the HomeTours
   */
  static class HomeTourViewHolder extends RecyclerView.ViewHolder {

    private MaterialCardView cardView;
    private TextView titleView;
    private TextView subtitleView;
    private IconicsImageView imageView;
    private IconicsImageView menuImageView;
    private IconicsTextView tourTravelTimeView;
    private TextView tourTagTitle;
    private FlowLayout tourTagsLayout;

    /**
     * Constructor for the HomeTour holder
     *
     * @param itemView the view we have a reference to in our holder
     */
    public HomeTourViewHolder(@NonNull View itemView) {
      super(itemView);

      // link UI elements to views
      cardView = itemView.findViewById(R.id.card_tour);
      titleView = itemView.findViewById(R.id.card_tour_title);
      subtitleView = itemView.findViewById(R.id.card_tour_subtitle);

      tourTagsLayout = itemView.findViewById(R.id.tour_tags_layout);
      tourTagTitle = itemView.findViewById(R.id.tour_tag_title);

      imageView = itemView.findViewById(R.id.card_tour_img);
      imageView.setIcon(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_image).color(Color.GRAY).sizeDp(24));
      tourTravelTimeView = itemView.findViewById(R.id.card_tour_eta);
      tourTravelTimeView.setDrawableStart(
          new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_clock)
              .sizeDp(20)
              .paddingDp(4)
              .color(itemView.getContext().getColor(R.color.gray)));

      menuImageView = itemView.findViewById(R.id.card_tour_menu);
      menuImageView.setIcon(new IconicsDrawable(itemView.getContext()).icon(MaterialDesignIconic.Icon.gmi_more_vert).color(itemView.getContext().getColor(R.color.light_gray)).sizeDp(14));
      imageView = itemView.findViewById(R.id.card_tour_img);
      imageView.setIcon(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_image)
          .color(Color.GRAY)
          .sizeDp(24));

      menuImageView = itemView.findViewById(R.id.card_tour_menu);
      menuImageView.setIcon(
          new IconicsDrawable(itemView.getContext()).icon(MaterialDesignIconic.Icon.gmi_more_vert)
              .color(itemView.getContext().getColor(R.color.light_gray))
              .sizeDp(14));
    }
  }
}
