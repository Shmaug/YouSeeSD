package com.beep.youseesd.util;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

/**
 * Utility class to help streamline image modifications
 */
public class ImageUtil {

  /**
   * Computes a new ColorMatrix to use as a filter
   *
   * @param contrast the contrast we want as a float
   * @param brightness the brightness we want as a float
   * @return the new ColorMatrixColorFilter based on the constrast and brightness
   */
  public static ColorMatrixColorFilter changeBitmapContrastBrightness(float contrast,
      float brightness) {
    ColorMatrix cm = new ColorMatrix(new float[]
        {
            contrast, 0, 0, 0, brightness,
            0, contrast, 0, 0, brightness,
            0, 0, contrast, 0, brightness,
            0, 0, 0, 1, 0
        });

    return new ColorMatrixColorFilter(cm);
  }
}
