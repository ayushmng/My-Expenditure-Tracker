package com.ayush.myexpendituretracker.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.View;

public class MySharedPreferences {
    Context context;
    SharedPreferences sharedPreferences;

    private static final String SHARED_NAME = "sharedPreference";
    private static final String STATUS = "status";
    private static final String USERNAME = "user_name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String MONTHLYINCOME = "montly_income";
    private static final String SAVING = "saving";

    public MySharedPreferences(Context context){
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        sharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }

    public void setStatus(int status) {
        sharedPreferences.edit().putInt(STATUS, status).apply();
    }

    public int getStatus() {
        return sharedPreferences.getInt(STATUS, 0);
    }

    public void setUsername(String username) {
        sharedPreferences.edit().putString(USERNAME, username).apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME, "");
    }

    public void setEmail(String email) {
        sharedPreferences.edit().putString(EMAIL, email).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, "");
    }

    public void setPassword(String password) {
        sharedPreferences.edit().putString(PASSWORD, password).apply();
    }

    public String getPassword() {
        return sharedPreferences.getString(PASSWORD, "");
    }

    public void setMonthlyincome(String monthlyincome) {
        sharedPreferences.edit().putString(MONTHLYINCOME, monthlyincome).apply();
    }

    public String getMonthlyincome() {
        return sharedPreferences.getString(MONTHLYINCOME, "");
    }

    public void setSaving(String saving) {
        sharedPreferences.edit().putString(SAVING, saving).apply();
    }

    public String getSaving() {
        return sharedPreferences.getString(SAVING, "");
    }

    public void removealldata(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply(); // to remove preference values
    }

    /*public MySharedPreferences(Context context, int status, String username, String email, String password){
        this.context = context;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.key), status);
        editor.putString(USERNAME, username);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.apply();
    }*/
}
