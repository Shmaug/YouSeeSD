package com.beep.youseesd.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.beep.youseesd.R;
import com.pedromassango.ibackdrop.Backdrop;

public class CreateTourActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Backdrop backdrop;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backdrop.closeBackdrop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);
        toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("");

        backdrop = (Backdrop) findViewById(R.id.backdrop_view);
    }
}
