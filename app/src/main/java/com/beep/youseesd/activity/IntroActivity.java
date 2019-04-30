package com.beep.youseesd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.beep.youseesd.R;
import com.beep.youseesd.util.WLog;

public class IntroActivity extends AppCompatActivity {

    private Button mGoTourButton;
    private Button mGoLoginButton;
    private Button mGoSignupButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        WLog.i("intro launched");

        mGoTourButton = (Button) findViewById(R.id.go_tour);
        mGoTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OnTourActivity.class);
                startActivity(intent);
            }
        });

        mGoLoginButton = (Button) findViewById(R.id.go_login);
        mGoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        mGoSignupButton = (Button) findViewById(R.id.go_signup);
        mGoSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
