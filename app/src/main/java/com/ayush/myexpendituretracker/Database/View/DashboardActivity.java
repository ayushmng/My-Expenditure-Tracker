package com.ayush.myexpendituretracker.Database.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayush.myexpendituretracker.Database.MyExpenditureModel;
import com.ayush.myexpendituretracker.Database.MyExpenditureViewModel;
import com.ayush.myexpendituretracker.LoginActivity;
import com.ayush.myexpendituretracker.R;
import com.ayush.myexpendituretracker.SharedPreference.MySharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private MyExpenditureViewModel myExpenditureViewModel;
    private MySharedPreferences mySharedPreferences;
    private DashboardAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<MyExpenditureModel> arrayList;

    private ImageButton edit;
    private TextView salutation, monthlyIncome, expectedSaving;
    private String MI, ES;

    private long mBackPressed;
    private static final int TIME_INTERVAL = 3000; // milliseconds, desired time passed between two back presses.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findviews();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValuesforExpenditureAndSaving();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DashboardActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new DashboardAdapter(DashboardActivity.this, arrayList);
        recyclerView.setAdapter(adapter);

     /*   myExpenditureViewModel = ViewModelProviders.of(this).get(MyExpenditureViewModel.class);
        myExpenditureViewModel.getGetAllData().observe(this, new Observer<List<MyExpenditureModel>>() {
            @Override
            public void onChanged(@Nullable List<MyExpenditureModel> data) {
                try {
                    *//*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DashboardActivity.this);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    adapter = new DashboardAdapter(DashboardActivity.this, arrayList);
                    recyclerView.setAdapter(adapter);*//*
//                    email.setText((Objects.requireNonNull(data).get(0).getEmail()));
//                    password.setText((Objects.requireNonNull(data.get(0).getPassword())));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });*/

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValuesforTodayExpenditure();
            }
        });
    }

    private void findviews() {
        salutation = findViewById(R.id.salutation);
        edit = findViewById(R.id.edit_button);
        monthlyIncome = findViewById(R.id.monthly_income);
        expectedSaving = findViewById(R.id.expected_saving);
        recyclerView = findViewById(R.id.recyclerView);
        mySharedPreferences = new MySharedPreferences(this);
        arrayList = new ArrayList<>();

        MI = mySharedPreferences.getMonthlyincome();
        ES = mySharedPreferences.getSaving();

        if (!mySharedPreferences.getUsername().isEmpty()) {
            salutation.setText("Hello, " + mySharedPreferences.getUsername());
        }

        if (!MI.isEmpty() && !ES.isEmpty()) {
            monthlyIncome.setText("Your Monthly Income: " + MI);
            expectedSaving.setText("Your Expected Saving: " + ES);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        Drawable yourdrawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN); // change color of menu icon
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                LogoutAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setValuesforExpenditureAndSaving() {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.amount_holder, null);
        final EditText monthly_income = view.findViewById(R.id.edt_monthly_income);
        final EditText expected_saving = view.findViewById(R.id.edt_expected_salary);

        mBuilder.setCancelable(false);
        mBuilder.setView(view);
        mBuilder.setTitle("My Expenditure Tracker");

        MI = mySharedPreferences.getMonthlyincome();
        ES = mySharedPreferences.getSaving();

        if (!MI.isEmpty() && !ES.isEmpty()) {
            monthly_income.setText(MI);
            expected_saving.setText(ES);
        }

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String mi = monthly_income.getText().toString();
                String es = expected_saving.getText().toString();

                if (mi.equals("") || mi.isEmpty() && es.equals("") || es.isEmpty()) {
                    monthly_income.setError("Amount required");
                    expected_saving.setError("Amount required");
                    Toast.makeText(DashboardActivity.this, "Please provide amount", Toast.LENGTH_SHORT).show();
                } else {
                    if (Integer.parseInt(es) > Integer.parseInt(mi)) {
                        Toast.makeText(DashboardActivity.this, "Sorry, your Monthly Income can't be greater than Saving", Toast.LENGTH_SHORT).show();
                    } else {
                        mySharedPreferences.setMonthlyincome(mi);
                        mySharedPreferences.setSaving(es);
                        Toast.makeText(DashboardActivity.this, "Amount " + mi + " as monthly income and " + es + " as expected saving has been added to your account", Toast.LENGTH_LONG).show();

                        monthlyIncome.setText("Your Monthly Income: " + mi);
                        expectedSaving.setText("Your Expected Saving: " + es);
                    }
                }
            }
        });

        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mBuilder.setCancelable(true);
            }
        });

        mBuilder.create().show();  // Displays the dialogue box
    }

    private void setValuesforTodayExpenditure() {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.today_expenditure, null);
        final EditText title = view.findViewById(R.id.edt_title);
        final EditText today_exp = view.findViewById(R.id.edt_today_expenditure);

        mBuilder.setCancelable(false);
        mBuilder.setView(view);
        mBuilder.setTitle("My Today Expenditure");

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String ttl = title.getText().toString();
                String te = today_exp.getText().toString();

                MyExpenditureModel expenditureModel = new MyExpenditureModel();

                if (ttl.equals("") || ttl.isEmpty() && te.equals("") || te.isEmpty()) {
                    title.setError("Title required");
                    today_exp.setError("Amount required");
                    Toast.makeText(DashboardActivity.this, "Please provide title and amount", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    String todayDate = formatter.format(date);
                    expenditureModel.setTitle(ttl);
                    expenditureModel.setExpenditure(te);
                    expenditureModel.setDate(todayDate);
                    myExpenditureViewModel.insert(expenditureModel);
                    Toast.makeText(DashboardActivity.this, "You set amount " + te + " as " + ttl + " for today expenditure", Toast.LENGTH_LONG).show();
                }
            }
        });

        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mBuilder.setCancelable(true);
            }
        });

        mBuilder.create().show();  // Displays the dialogue box
    }

    private void LogoutAlertDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle("Logout");
        builder.setCancelable(false);
        builder.setMessage("Are you sure ? Logging out will clear all your data");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mySharedPreferences.removealldata();
                ;
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Logged Out !!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);  // To exit the whole application
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }
}