package com.beep.youseesd.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beep.youseesd.R;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.view.IconicsImageView;

public class PlaceContentRowView extends LinearLayout {

    private IconicsImageView mTagView;
    private TextView mContentTextView;

    public PlaceContentRowView(Context context, ViewGroup root) {
        super(context);
        inflate(context, R.layout.view_place_content_row, root);

        mTagView = findViewById(R.id.view_place_tag);
        mContentTextView = findViewById(R.id.view_place_content);
    }

    public void setupRow(IIcon icon, String content) {
        mTagView.setIcon(new IconicsDrawable(getContext())
                .icon(icon)
                .color(getContext().getResources().getColor(R.color.primaryColor))
                .sizeDp(18));

        mContentTextView.setText(content);
    }
}
