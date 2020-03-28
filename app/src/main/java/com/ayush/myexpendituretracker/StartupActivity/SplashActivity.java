package com.ayush.myexpendituretracker.StartupActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ayush.myexpendituretracker.OnBoarding.OnBoardingActivity;
import com.ayush.myexpendituretracker.R;
import com.ayush.myexpendituretracker.SharedPreference.MySharedPreferences;

public class SplashActivity extends AppCompatActivity {

    MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final String PREFS_NAME = "MyPrefsFile";
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        mySharedPreferences = new MySharedPreferences(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (settings.getBoolean("my_first_time", true)) {
                    //the app is being launched for first time, do something
                    startActivity(new Intent(SplashActivity.this, OnBoardingActivity.class));
                    // record the fact that the app has been started at least once
                    settings.edit().putBoolean("my_first_time", false).commit();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }

                // To get direct access to dashboard
                /*int status = mySharedPreferences.getStatus();
                if (status == 1) {
                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }*/
            }
        }, 1500);
    }
}
