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

    FragmentTransaction fragmentTransaction;

    @Override
    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(newBase);
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
    }

    /**
     * Puts a new fragment on the screen.
     *
     * @param fragment
     * @param stackName if valid, will put this screen on the stack
     */
    public void updateFragment(Fragment fragment, String stackName) {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainframe, fragment);
        fragmentTransaction.commit();

        if (stackName != null) {
            fragmentTransaction.addToBackStack(stackName);
        }
    }
}
