package com.beep.youseesd.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.beep.youseesd.R;
import com.beep.youseesd.application.App;
import com.beep.youseesd.model.Theme;
import com.beep.youseesd.model.Tour;
import com.beep.youseesd.model.TourSet;
import com.beep.youseesd.util.DatabaseUtil;
import com.beep.youseesd.util.WLog;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import java.util.ArrayList;
import java.util.List;

/**
 * The screen for the "Create Tour" part of our app
 */
public class CreateTourActivity extends AppCompatActivity {

  private MaterialToolbar mToolbar;
  private ChipGroup mChipGroup;

  /**
   * Closes the backdrop when the back button is pressed
   */
  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  /**
   * Method that will handle the logic for the creation of the tour once the user is done
   *
   * @param item the item that was clicked on
   * @return returns true if we were able to create the tour or the super method's result
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_create_tour_ok:
        List<Chip> selectedChips = filterSelectedChips(findChips(mChipGroup));
        List<Theme> selectedThemes = generateThemes(selectedChips);

        // create a tour based on the themes that were selected and write to database
        TourSet ts = new TourSet();
        Tour t = ts.findBestFitTour(selectedThemes);
        DatabaseUtil.createTour(App.getUser().getUid(), t,
            (databaseError, databaseReference) -> {
              WLog.i("done posting tour object!");
              finish();
            });
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Lifecycle method that creates the menu for us
   *
   * @param menu the menu we want to create
   * @return true if it was able to create the menu
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_create_tour, menu);

    // sets the "create" button to be a check mark
    menu.findItem(R.id.menu_create_tour_ok).setIcon(
        new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_check)
            .actionBar().color(Color.WHITE)
    );
    return true;
  }

  /**
   * Stores references to our UI elements
   *
   * @param savedInstanceState used in the super method
   */
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_tour);
    mToolbar = findViewById(R.id.create_toolbar);
    setSupportActionBar(mToolbar);
    mChipGroup = findViewById(R.id.chip_group);
  }

  /**
   * Generates a Theme object given the id of a chip that was selected. Helper method for
   * generateThemes
   *
   * @param viewId the id of the chip that was selected
   * @return a Theme accurately reflecting the Theme of the button
   */
  private Theme generateTheme(@IdRes int viewId) {
    // switch-case for the predefined themes that have been specified in the TourSet model
    switch (viewId) {
      case R.id.tag_warren:
        return new Theme(TourSet.TAG_WARREN, TourSet.THEME_MAP.get(TourSet.TAG_WARREN));
      case R.id.tag_erc:
        return new Theme(TourSet.TAG_ERC, TourSet.THEME_MAP.get(TourSet.TAG_ERC));
      case R.id.tag_sixth:
        return new Theme(TourSet.TAG_SIXTH, TourSet.THEME_MAP.get(TourSet.TAG_SIXTH));
      case R.id.tag_marshall:
        return new Theme(TourSet.TAG_MARSHAL, TourSet.THEME_MAP.get(TourSet.TAG_MARSHAL));
      case R.id.tag_muir:
        return new Theme(TourSet.TAG_MUIR, TourSet.THEME_MAP.get(TourSet.TAG_MUIR));
      case R.id.tag_revelle:
        return new Theme(TourSet.TAG_REVELLE, TourSet.THEME_MAP.get(TourSet.TAG_REVELLE));
      case R.id.tag_fitness:
        return new Theme(TourSet.TAG_FITNESS, TourSet.THEME_MAP.get(TourSet.TAG_FITNESS));
      case R.id.tag_social:
        return new Theme(TourSet.TAG_SOCIAL, TourSet.THEME_MAP.get(TourSet.TAG_SOCIAL));
      case R.id.tag_engineering:
        return new Theme(TourSet.TAG_ENGINEERING, TourSet.THEME_MAP.get(TourSet.TAG_ENGINEERING));
      case R.id.tag_food_living:
        return new Theme(TourSet.TAG_FOOD_LIVING, TourSet.THEME_MAP.get(TourSet.TAG_FOOD_LIVING));
      case R.id.tag_humanities:
        return new Theme(TourSet.TAG_HUMANITIES, TourSet.THEME_MAP.get(TourSet.TAG_HUMANITIES));
      case R.id.tag_popular:
        return new Theme(TourSet.TAG_POPULAR, TourSet.THEME_MAP.get(TourSet.TAG_POPULAR));
      case R.id.tag_natural_sciences:
        return new Theme(TourSet.TAG_NATURAL_SCIENCES,
            TourSet.THEME_MAP.get(TourSet.TAG_NATURAL_SCIENCES));
    }

    return null;
  }

  /**
   * Generate a list of Themes using all of the chips' ids
   *
   * @param chips the list of chips that were selected by the user
   * @return a list of Themes, one for each chip
   */
  private List<Theme> generateThemes(List<Chip> chips) {
    List<Theme> themes = new ArrayList<>();

    for (Chip c : chips) {
      themes.add(generateTheme(c.getId()));
    }

    return themes;
  }

  /**
   * Filters the list of chips by the ones that were selected by the user
   *
   * @param chips the list of chips on our activity
   * @return a list of chips that were selected by the user
   */
  private List<Chip> filterSelectedChips(List<Chip> chips) {
    List<Chip> selectedChips = new ArrayList<>();

    for (Chip c : chips) {
      if (c.isChecked()) {
        selectedChips.add(c);
      }
    }

    return selectedChips;
  }

  /**
   * Finds all of the chips on our UI and adds them into a group
   *
   * @param group the group of chips from our UI
   * @return a list of each chip within the ChipGroup
   */
  private List<Chip> findChips(ChipGroup group) {
    List<Chip> chips = new ArrayList<>();

    for (int i = 0; i < group.getChildCount(); i++) {
      Chip c = (Chip) group.getChildAt(i);
      chips.add(c);
    }

    return chips;
  }
}
