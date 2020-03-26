package com.ayush.myexpendituretracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ayush.myexpendituretracker.Database.View.DashboardActivity;
import com.ayush.myexpendituretracker.SharedPreference.MySharedPreferences;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText username, email, password;
    TextInputLayout til_uname, til_email, til_pass;
    TextView title, logintext, textView;
    String Email, Password;
    Button signup;

    MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        findviews();
        title.setText("Login");

        mySharedPreferences = new MySharedPreferences(LoginActivity.this);
        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (logintext.getText().equals("Login")) {
                    title.setText("Login");
                    textView.setText("Haven't registered yet?");
                    logintext.setText("Register");
                    til_uname.setVisibility(View.GONE);
                    signup.setText("Login");

                } else if (logintext.getText().equals("Register")) {
                    title.setText("Register");
                    textView.setText(R.string.bottom_text);
                    logintext.setText("Login");
                    til_uname.setVisibility(View.VISIBLE);
                    signup.setText(R.string.sign_up);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signup.getText().toString().toLowerCase().equals("sign up")) {
                    if (checklogin()) {
                        mySharedPreferences.setStatus(1);
                        mySharedPreferences.setUsername(username.getText().toString());
                        mySharedPreferences.setEmail(email.getText().toString());
                        mySharedPreferences.setPassword(password.getText().toString());
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        Toast.makeText(LoginActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    }
                } else if (signup.getText().toString().toLowerCase().equals("login")) {

                    if (checklogin()) {
                        try {
                            String email = mySharedPreferences.getEmail();
                            String password = mySharedPreferences.getPassword();

                            if (!email.equals("") && !password.equals("") && email.equals(Email) && password.equals(Password)) {
                                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Email or Password did not match", Toast.LENGTH_SHORT).show();
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        });
    }

    private void findviews() {

        title = findViewById(R.id.title);
        logintext = findViewById(R.id.login_textView);
        textView = findViewById(R.id.textView);
        signup = findViewById(R.id.login);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        til_uname = findViewById(R.id.til_username);
        til_email = findViewById(R.id.til_email);
        til_pass = findViewById(R.id.til_password);
    }

    private boolean checklogin() {

        Email = email.getText().toString();
        Password = password.getText().toString();

        //Checking validity only for Register Screen:
        if (logintext.getText().equals("Login")) {
            //Check for valid Username:
            if (TextUtils.isEmpty(username.getText().toString())) {
                username.setError(getString(R.string.error_field_required));
                return false;
            }
        }

        // Check for a valid email address:
        if (TextUtils.isEmpty(Email)) {
            email.setError(getString(R.string.error_field_required));
            signup.setVisibility(View.VISIBLE);
            return false;
        }

        // Checks whether input is email or number:
        boolean digitsOnly = TextUtils.isDigitsOnly(email.getText());
        if (digitsOnly) {
            if (isNumber(Email)) { // is a number
                String pattern = Email.substring(0, 2);
                if (pattern.equals("98") && Email.length() == 10) {
//                    Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    email.setError("Invalid Phone Number");
                    return false;
                }
            }
        } else {
            if (!isEmailValid(Email)) {
                email.setError(getString(R.string.error_invalid_email));
                return false;
            }
        }

        // Check for a valid password:
        if (password.getText() == null || password.getText().length() < 4) {
            password.setError(getString(R.string.error_invalid_password));
            signup.setVisibility(View.VISIBLE);
            return false;
        }

        return true;
    }

    private boolean isNumber(String text) {
        try {
            long num = Long.parseLong(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
