package com.beep.youseesd.view;

import android.content.Context;
import com.beep.youseesd.R;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsTextView;

/**
 * View class that handles the display of the hashtags on the home page
 */
public class TourTagTextView extends IconicsTextView {

  /**
   * Constructor that initializes basic display
   *
   * @param context the context of our app
   * @param tagLabel the label we want the hashtag to be
   */
  public TourTagTextView(Context context, String tagLabel) {
    super(context);

    setDrawableStart(generateHashtagIcon());
    setTextColor(getResources().getColor(R.color.gray));
    setText(tagLabel);

    int padding = (int) getResources().getDimension(R.dimen.item_tour_hash_text_padding);
    setPadding(0, 0, padding, 0);
  }

  /**
   * Helper method that helps generate a Drawable icon of the hashtag
   *
   * @return IconicsDrawable of a hashtag
   */
  private IconicsDrawable generateHashtagIcon() {
    return new IconicsDrawable(getContext())
        .icon(FontAwesome.Icon.faw_hashtag)
        .color(getResources().getColor(R.color.gray))
        .sizeDp(12);
  }
}
