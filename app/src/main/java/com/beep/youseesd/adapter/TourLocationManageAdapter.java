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
import com.beep.youseesd.model.TourLocation;
import com.beep.youseesd.model.TourStop;
import com.google.android.material.button.MaterialButton;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TourLocationManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TOUR_LOCATION_VIEW = 0x01;
    private static final int TOUR_END_FOOTER_VIEW = 0x02;

    private OnTourActivity mActivity;
    private List<TourStop> mStops;


    public TourLocationManageAdapter(OnTourActivity act, TourStop[] stops) {
        super();
        mStops = new ArrayList<>();
        mStops.addAll(Arrays.asList(stops));
        this.mActivity = act;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mStops.size()) {
            return TOUR_END_FOOTER_VIEW;
        }

        return TOUR_LOCATION_VIEW;
    }

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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof TourLocationManageViewHolder) {
            TourLocationManageViewHolder h = (TourLocationManageViewHolder) holder;
            TourLocation t = mStops.get(i).getTourLocation();
            h.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!t.isVisited()) {
                        t.visitedTimestamp = Calendar.getInstance().getTimeInMillis();
                    } else {
                        t.visitedTimestamp = 0;
                    }
                    notifyDataSetChanged();
                    mActivity.updateLocationPinMarkerVisited(t.isVisited(), i);
                    mActivity.updateBottomSheetCollapsed(mStops.get(i));
                }
            });

            h.mTitleView.setText(t.title);
            if (t.isVisited()) {
                long time = Calendar.getInstance().getTimeInMillis() - t.visitedTimestamp;
                if (time < 1000) {
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
            EndTourFooterViewHolder h = (EndTourFooterViewHolder) holder;
            h.mEndTourButton.setOnClickListener(mActivity);
        }
    }

    @Override
    public int getItemCount() {
        return mStops.size() + 1;
    }

    static class TourLocationManageViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleView;
        private IconicsImageView mCheckImageView;
        private TextView mSubtitleView;
        private LinearLayout mLayout;

        public TourLocationManageViewHolder(@NonNull View itemView) {
            super(itemView);

            mLayout = (LinearLayout) itemView.findViewById(R.id.item_tour_location_layout);
            mTitleView = (TextView) itemView.findViewById(R.id.item_tour_location_title);
            mSubtitleView = (TextView) itemView.findViewById(R.id.item_tour_location_subtitle);
            mCheckImageView = (IconicsImageView) itemView.findViewById(R.id.item_tour_location_checkview);
        }
    }

    static class EndTourFooterViewHolder extends RecyclerView.ViewHolder {

        private View mEndTourButton;

        EndTourFooterViewHolder(@NonNull View itemView) {
            super(itemView);
            mEndTourButton = (MaterialButton) itemView.findViewById(R.id.item_end_tour_btn);
        }
    }
}
