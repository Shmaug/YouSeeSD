package com.beep.youseesd.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.beep.youseesd.R;
import com.mikepenz.iconics.context.IconicsContextWrapper;

public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(newBase);
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void updateFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mainframe, fragment);
        transaction.commit();
    }
}
