package com.beep.youseesd.view;

import android.content.Context;
import com.beep.youseesd.R;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsTextView;

public class TourTagTextView extends IconicsTextView {

  public TourTagTextView(Context context, String tagLabel) {
    super(context);

    setDrawableStart(generateHashtagIcon());
    setTextColor(getResources().getColor(R.color.gray));
    setText(tagLabel);

    int padding = (int) getResources().getDimension(R.dimen.item_tour_hash_text_padding);
    setPadding(0, 0, padding, 0);
  }

  private IconicsDrawable generateHashtagIcon() {
    return new IconicsDrawable(getContext())
        .icon(FontAwesome.Icon.faw_hashtag)
        .color(getResources().getColor(R.color.gray))
        .sizeDp(12);
  }
}
