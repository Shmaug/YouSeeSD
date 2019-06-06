package com.beep.youseesd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
public class IntroActivity extends AppCompatActivity implements OnCompleteListener<AuthResult>, View.OnClickListener {

  private CarouselView mCarouselView;
  private IconicsImageView mLogoImageView;
  private ImageView mUCSDLogoImageView;
  private MaterialButton mRegisterButton;
  private TextView mLoginLoadingTextView;

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

    setupUI();
    loadLogoImages();
    setupCrouselSwipingView();

    // setup the register button
    mRegisterButton.setOnClickListener(this);
  }

  /**
   * setup the carousel with default pictures for the user to swipe through
   */
  private void setupCrouselSwipingView() {
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
   * loads logo images
   */
  private void loadLogoImages() {
    Glide.with(this)
        .load(getResources()
            .getDrawable(R.drawable.intro_logo))
        .into(mLogoImageView);

    Glide.with(this)
        .load(R.drawable.ucsd_logo)
        .override(250, 50)
        .into(mUCSDLogoImageView);
  }

  /**
   * setup UI components in IntroActivity
   */
  private void setupUI() {
    mLogoImageView = findViewById(R.id.intro_logo_image);
    mLoginLoadingTextView = findViewById(R.id.login_guide);
    mRegisterButton = findViewById(R.id.intro_join_btn);
    mLogoImageView = findViewById(R.id.intro_logo_image);
    mUCSDLogoImageView = findViewById(R.id.intro_ucsd_logo_image);
    mCarouselView = findViewById(R.id.intro_carousel);
  }

  /**
   * Lifecycle method that gets called once we're done with the activity. This will create a user in
   * our database
   *
   * @param task the task that contains the result of our operation
   */
  @Override
  public void onComplete(@NonNull Task<AuthResult> task) {
    mLoginLoadingTextView.setVisibility(View.GONE);
    mRegisterButton.setEnabled(true);

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

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.intro_join_btn:
        mLoginLoadingTextView.setVisibility(View.VISIBLE);
        mRegisterButton.setEnabled(false);
        AuthHandler.signinAnonymously(IntroActivity.this, IntroActivity.this);
        break;
    }
  }
}
