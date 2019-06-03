package com.beep.youseesd.activity;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * Activity that is loaded if the user is not logged in
 */
public class IntroActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {

  private CarouselView mCarouselView;
  private IconicsImageView mLogoImageView;
  private ImageView mUCSDLogoImageView;
  private MaterialButton mRegisterButton;

  // images that will greet the user
  private static final String[] IMAGE_LINKS = {
      "https://ucpa.ucsd.edu/images/image_library/triton-fountain-at-price-center.jpg",
      "https://ucpa.ucsd.edu/images/image_library/Mayer-Hall-at-Revelle-College.jpg",
      "https://ucpa.ucsd.edu/images/image_library/wellsfargohall.jpg"
  };

  /**
   * Lifecycle method to create the activity with the right layout Registers the user's device once
   * they give us permission to do so
   *
   * @param savedInstanceState used in the super onCreate method
   */
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intro);

    // setup the register button
    mRegisterButton = findViewById(R.id.intro_join_btn);
    mRegisterButton.setOnClickListener(
        v -> AuthHandler.signinAnonymously(IntroActivity.this, IntroActivity.this));

    // setup the logo
    mLogoImageView = findViewById(R.id.intro_logo_image);
    Glide.with(this)
        .load(getResources()
            .getDrawable(R.drawable.intro_logo))
        .into(mLogoImageView);

    mUCSDLogoImageView = findViewById(R.id.intro_ucsd_logo_image);
    Glide.with(this)
        .load(R.drawable.ucsd_logo)
        .override(250, 50)
        .into(mUCSDLogoImageView);

    // setup the carousel with default pictures for the user to swipe through
    mCarouselView = findViewById(R.id.intro_carousel);
    mCarouselView.setPageCount(IMAGE_LINKS.length);
    mCarouselView.setImageListener((position, imageView) -> {
      Glide.with(imageView.getContext())
          .load(IMAGE_LINKS[position])
          .centerCrop()
          .into(imageView);

      imageView.setColorFilter(ImageUtil
          .changeBitmapContrastBrightness(0.5f, 0.7f));
    });
  }

  /**
   * Lifecycle method that gets called once we're done with the activity. This will create a user in
   * our database
   *
   * @param task the task that contains the result of our operation
   */
  @Override
  public void onComplete(@NonNull Task<AuthResult> task) {
    // create a new user in firebase and write to it
    FirebaseUser newUser = task.getResult().getUser();
    WLog.i("newUserId: " + newUser.getUid());

    // check to make sure we registered properly
    if (!newUser.getUid().isEmpty()) {
      Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
      startActivity(intent);
      finish();
    }
  }
}
