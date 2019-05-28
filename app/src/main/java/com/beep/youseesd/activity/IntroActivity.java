package com.beep.youseesd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.beep.youseesd.R;
import com.beep.youseesd.handler.AuthHandler;
import com.beep.youseesd.util.ImageUtil;
import com.beep.youseesd.util.WLog;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.iconics.view.IconicsImageView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class IntroActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {

    private CarouselView mCarouselView;
    private IconicsImageView mLogoImageView;
    private ImageView mUCSDLogoImageView;
    private MaterialButton mRegisterButton;

    private static final String IMAGE_LINKS[] = {
            "https://ucpa.ucsd.edu/images/image_library/geisel-dusk.jpg",
            "https://ucpa.ucsd.edu/images/image_library/Mayer-Hall-at-Revelle-College.jpg",
            "https://ucpa.ucsd.edu/images/image_library/Price-Center-West.jpg"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mRegisterButton = (MaterialButton) findViewById(R.id.intro_join_btn);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthHandler.signinAnonymously(IntroActivity.this, IntroActivity.this);
            }
        });
        mLogoImageView = (IconicsImageView) findViewById(R.id.intro_logo_image);
        Glide.with(this)
                .load(getResources()
                        .getDrawable(R.drawable.ic_launcher))
                .into(mLogoImageView);

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

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        FirebaseUser newUser = task.getResult().getUser();
        WLog.i("newUserId: " + newUser.getUid());
        if (!newUser.getUid().isEmpty()) {
            Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            WLog.i(newUser == null ? "newUser is null" : "newUser: " + newUser.getUid());
        }
    }
}
