package com.beep.youseesd.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.beep.youseesd.R;
import com.beep.youseesd.util.ImageUtil;
import com.bumptech.glide.Glide;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class IntroActivity extends AppCompatActivity {

    private CarouselView mCarouselView;
    private IconicsImageView mLogoImageView;
    private ImageView mUCSDLogoImageView;

    private static final String IMAGE_LINKS[] = {
            "https://ucpa.ucsd.edu/images/image_library/geisel-dusk.jpg",
            "https://ucpa.ucsd.edu/images/image_library/Mayer-Hall-at-Revelle-College.jpg",
            "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mLogoImageView = (IconicsImageView) findViewById(R.id.intro_logo_image);
        mLogoImageView.setIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_grin).color(Color.WHITE).paddingDp(3).sizeDp(40));

        mUCSDLogoImageView = (ImageView) findViewById(R.id.intro_ucsd_logo_image);
        Glide.with(this).load(R.drawable.ucsd_logo).override(250, 50).into(mUCSDLogoImageView);

        mCarouselView = (CarouselView) findViewById(R.id.intro_carousel);
        mCarouselView.setPageCount(IMAGE_LINKS.length);
        mCarouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(imageView.getContext())
                        .load(IMAGE_LINKS[position])
                        .centerCrop()
                        .into(imageView);

                imageView.setColorFilter(ImageUtil.changeBitmapContrastBrightness(0.5f, 0.7f));
            }
        });
    }
}
