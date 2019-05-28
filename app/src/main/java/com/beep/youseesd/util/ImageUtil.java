package com.beep.youseesd.util;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class ImageUtil {
    public static ColorMatrixColorFilter changeBitmapContrastBrightness(float contrast, float brightness) {
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
