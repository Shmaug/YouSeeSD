package com.beep.youseesd.view;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    int space;
    boolean isHorizontalLayout;

    public DividerItemDecoration(int space) {
        this.space = space;
    }

    public DividerItemDecoration(int space, boolean isHorizontalLayout) {
        this.space = space;
        this.isHorizontalLayout = isHorizontalLayout;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (isHorizontalLayout) {
            outRect.bottom = space;
            outRect.right = space;
            outRect.left = space;
            outRect.top = space;

        } else {
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0)
                outRect.top = space;
            else
                outRect.top = 0;
        }
    }
}
