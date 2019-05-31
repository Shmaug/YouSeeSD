package com.beep.youseesd.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.bumptech.glide.Glide;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.card.MaterialCardView;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.mikepenz.iconics.view.IconicsTextView;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import java.util.List;

public class HomeTourAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int TOUR_VIEW = 0x01;
  private static final int FOOTER_VIEW = 0x02;

  private List<Tour> mTours;
  private HomeActivity act;

  public HomeTourAdapter(HomeActivity act, List<Tour> tours) {
    super();
    this.mTours = tours;
    this.act = act;
  }

  public void updateTours(List<Tour> tours) {
    this.mTours = tours;
    notifyDataSetChanged();
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
    if (viewType == FOOTER_VIEW) {
      View footerView = inflater.inflate(R.layout.item_footer, parent, false);
      return new FooterViewHolder(footerView);
    }

    View v = inflater.inflate(R.layout.item_tour2, parent, false);
    return new HomeTourViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
    if (holder instanceof HomeTourViewHolder) {
      HomeTourViewHolder h = (HomeTourViewHolder) holder;
      Tour t = mTours.get(i);
      h.titleView.setText(mTours.get(i).title);
      h.subtitleView.setText(mTours.get(i).subtitle);
      Glide.with(h.imageView.getContext())
          .load(mTours.get(i).imageUrl)
          .centerCrop()
          .into(h.imageView);
      h.tourTravelTimeView.setText(mTours.get(i).estimatedTime + " mins");

      h.cardView.setOnClickListener(v -> {
        switch (act.getAppBar().getFabAlignmentMode()) {
          case BottomAppBar.FAB_ALIGNMENT_MODE_CENTER:
            act.getAppBar().setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
            act.getAppBar().setFabAnimationMode(BottomAppBar.FAB_ANIMATION_MODE_SCALE);
            act.getWeatherTextView().setVisibility(View.GONE);

            act.getFAB().setImageDrawable(new IconicsDrawable(act).icon(MaterialDesignIconic.Icon.gmi_play).color(Color.WHITE).sizeDp(24));

            ConfirmOnTourFragment fragment = new ConfirmOnTourFragment();
            Bundle b = new Bundle();
            b.putString(DatabaseUtil.TOUR_ID, t.tourId);
            fragment.setArguments(b);
            act.updateFragment(fragment, "ConfirmOnTour");
            act.getFAB().setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OnTourActivity.class);
                intent.putExtra(DatabaseUtil.TOUR_ID, t.tourId);
                act.startActivity(intent);
              }
            });
            break;
        }
      });

      h.menuImageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          PopupMenu popup = new PopupMenu(act, v);
          MenuInflater inflater = popup.getMenuInflater();
          inflater.inflate(R.menu.menu_tour_card, popup.getMenu());
          popup.show();
          popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
              switch (item.getItemId()) {
                case R.id.delete_tour:
                  DatabaseUtil.deleteTour(App.getUser().getUid(), t.tourId);
                  return true;
              }

              return false;
            }
          });
        }
      });

    } else if (holder instanceof FooterViewHolder) {
      FooterViewHolder h = (FooterViewHolder) holder;
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
    private IconicsTextView hashTextView3;
    private IconicsImageView menuImageView;
    private IconicsTextView tourTravelTimeView;

    public HomeTourViewHolder(@NonNull View itemView) {
      super(itemView);
      cardView = (MaterialCardView) itemView.findViewById(R.id.card_tour);

      titleView = (TextView) itemView.findViewById(R.id.card_tour_title);
      subtitleView = (TextView) itemView.findViewById(R.id.card_tour_subtitle);
      tourTravelTimeView = (IconicsTextView) itemView.findViewById(R.id.card_tour_eta);
      tourTravelTimeView.setDrawableStart(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_clock).sizeDp(20).paddingDp(4).color(itemView.getContext().getColor(R.color.gray)));

      imageView = (IconicsImageView) itemView.findViewById(R.id.card_tour_img);
      imageView.setIcon(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_image).color(Color.GRAY).sizeDp(24));

      hashTextView1 = (IconicsTextView) itemView.findViewById(R.id.card_tour_hash1);
      hashTextView1.setDrawableStart(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_hashtag).color(hashTextView1.getResources().getColor(R.color.dark_gray)).sizeDp(12));

      hashTextView2 = (IconicsTextView) itemView.findViewById(R.id.card_tour_hash2);
      hashTextView2.setDrawableStart(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_hashtag).color(hashTextView2.getResources().getColor(R.color.dark_gray)).sizeDp(12));

      hashTextView2 = (IconicsTextView) itemView.findViewById(R.id.card_tour_hash3);
      hashTextView2.setDrawableStart(new IconicsDrawable(itemView.getContext()).icon(FontAwesome.Icon.faw_hashtag).color(hashTextView2.getResources().getColor(R.color.dark_gray)).sizeDp(12));

      menuImageView = (IconicsImageView) itemView.findViewById(R.id.card_tour_menu);
      menuImageView.setIcon(new IconicsDrawable(itemView.getContext()).icon(MaterialDesignIconic.Icon.gmi_more_vert).color(itemView.getContext().getColor(R.color.light_gray)).sizeDp(14));
    }
  }
}
