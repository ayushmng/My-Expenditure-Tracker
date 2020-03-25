package com.ayush.myexpendituretracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.ayush.myexpendituretracker.SharedPreference.MySharedPreferences;

public class SplashActivity extends AppCompatActivity {

    private int SPLASH_TIME_OUT = 1500;
    MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mySharedPreferences = new MySharedPreferences(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                int status = mySharedPreferences.getStatus();
                if (status == 1) {
                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }
        }, SPLASH_TIME_OUT);
    }
}