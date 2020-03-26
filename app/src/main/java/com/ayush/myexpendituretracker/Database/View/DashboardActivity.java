package com.ayush.myexpendituretracker.Database.View;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayush.myexpendituretracker.DAO.LastMonthExpenditure;
import com.ayush.myexpendituretracker.DAO.TotalExpenditure;
import com.ayush.myexpendituretracker.Database.MyExpenditureModel;
import com.ayush.myexpendituretracker.Database.MyExpenditureViewModel;
import com.ayush.myexpendituretracker.LoginActivity;
import com.ayush.myexpendituretracker.R;
import com.ayush.myexpendituretracker.SharedPreference.MySharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private MyExpenditureViewModel myExpenditureViewModel;
    private MyExpenditureModel expenditureModel, expenditureModel2;
    private MySharedPreferences mySharedPreferences;
    private DashboardAdapter adapter;
    private RecyclerView recyclerView;

    public List<LastMonthExpenditure> lastMonthExpenditureList;

    private ImageButton edit;
    private TextView salutation, monthlyIncome, expectedSaving, totalExpenditure, topExpenditure;
    private String MI, ES, TE, todayDate, thisMonth;

    private long mBackPressed;
    private static final int TIME_INTERVAL = 3000; // milliseconds, desired time passed between two back presses.

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        findViews();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        todayDate = formatter.format(date);

        Calendar cal = Calendar.getInstance();
        thisMonth = new SimpleDateFormat("MMMM").format(cal.getTime());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValuesForExpenditureAndSaving();
            }
        });

        loadAllViewModels();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValuesForTodayExpenditure();
            }
        });
    }

    private void loadAllViewModels() {
        myExpenditureViewModel = ViewModelProviders.of(this).get(MyExpenditureViewModel.class);
        myExpenditureViewModel.getLastMonthDetails("February").observe(this, new Observer<List<LastMonthExpenditure>>() {
            @Override
            public void onChanged(List<LastMonthExpenditure> models) {
                if (models.size() > 0) {
                    Log.i("show_data", models.get(0).getDate() + " " + models.get(0).getTitle());
                    lastMonthExpenditureList = models;
                    lastMonthExpenditureList.addAll(models);
                }
            }
        });

        myExpenditureViewModel.getCurrentMonthDetails(thisMonth).observe(this, new Observer<List<MyExpenditureModel>>() {
            @Override
            public void onChanged(List<MyExpenditureModel> models) {

                if (models.size() > 0) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DashboardActivity.this);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    adapter = new DashboardAdapter(DashboardActivity.this, models, lastMonthExpenditureList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        myExpenditureViewModel.getTotalExpenditure(thisMonth).observe(this, new Observer<List<TotalExpenditure>>() {
            @Override
            public void onChanged(List<TotalExpenditure> models) {
                if (models.size() > 0) {
                    mySharedPreferences.setTotalExpenditure(models.get(0).getTotalExpenditure());
                    totalExpenditure.setText(models.get(0).getTotalExpenditure());
                }
            }
        });

        myExpenditureViewModel.getTopExp(thisMonth).observe(this, new Observer<List<MyExpenditureModel>>() {
            @Override
            public void onChanged(List<MyExpenditureModel> models) {
                if (models.size() > 0) {
                    String topExp = models.get(0).getExpenditure();
                    if (!topExp.isEmpty() || !topExp.equals("")) {
                        topExpenditure.setText(topExp);
                    }
                }
            }
        });

        TE = mySharedPreferences.getTotalExpenditure();
        if (!ES.isEmpty() && !TE.isEmpty()) {
            if (Integer.parseInt(ES) < Integer.parseInt(TE)) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                String titleText = "Alert !!";
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorRed));
                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
                ssBuilder.setSpan(
                        foregroundColorSpan,
                        0,
                        titleText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );

                builder.setTitle(ssBuilder);
                builder.setCancelable(false);
                builder.setMessage("Your Expenditure crosses your saving, Please limit your expenditure.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        builder.setCancelable(true);
                    }
                });
                builder.show();
            }
        }

    }

    private void findViews() {
        salutation = findViewById(R.id.salutation);
        edit = findViewById(R.id.edit_button);
        monthlyIncome = findViewById(R.id.monthly_income);
        expectedSaving = findViewById(R.id.expected_saving);
        recyclerView = findViewById(R.id.recyclerView);
        totalExpenditure = findViewById(R.id.total_exp);
        topExpenditure = findViewById(R.id.top_exp);

        mySharedPreferences = new MySharedPreferences(this);

        MI = mySharedPreferences.getMonthlyIncome();
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
        Drawable drawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        drawable.mutate();
        drawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN); // change color of menu icon
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

    private void setValuesForExpenditureAndSaving() {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.amount_holder, null);
        final EditText monthly_income = view.findViewById(R.id.edt_monthly_income);
        final EditText expected_saving = view.findViewById(R.id.edt_expected_salary);

        mBuilder.setCancelable(false);
        mBuilder.setView(view);
        mBuilder.setTitle("My Expenditure Tracker");

        MI = mySharedPreferences.getMonthlyIncome();
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
                        mySharedPreferences.setMonthlyIncome(mi);
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

    private void setValuesForTodayExpenditure() {

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

                expenditureModel = new MyExpenditureModel();
                expenditureModel2 = new MyExpenditureModel();

                if (ttl.equals("") || ttl.isEmpty() && te.equals("") || te.isEmpty()) {
                    title.setError("Title required");
                    today_exp.setError("Amount required");
                    Toast.makeText(DashboardActivity.this, "Please provide title and amount", Toast.LENGTH_SHORT).show();
                } else {

                    expenditureModel.setTitle(ttl);
                    expenditureModel.setExpenditure(te);
                    expenditureModel.setDate(todayDate);
                    expenditureModel.setMonth(thisMonth);
                    myExpenditureViewModel.insert(expenditureModel);

                    expenditureModel2.setTitle("Last_" + ttl);
                    expenditureModel2.setExpenditure(te);
                    expenditureModel2.setDate("01/02/2020");
                    expenditureModel2.setMonth("February");
                    myExpenditureViewModel.insert(expenditureModel2);

                    Toast.makeText(DashboardActivity.this, "You set amount " + te + " as " + ttl + " for today expenditure", Toast.LENGTH_LONG).show();

                    loadAllViewModels();
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

        final AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this, R.style.AlertDialogTheme);
        String titleText = "Logout";
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark));
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        builder.setTitle(ssBuilder);
        builder.setCancelable(true);
        builder.setMessage(R.string.logoutMessage);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mySharedPreferences.removealldata();
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Logged Out !!", Toast.LENGTH_SHORT).show();
            }
        });
        
        builder.setNeutralButton("Clear all record", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myExpenditureViewModel.delete();
                startActivity(new Intent(DashboardActivity.this, DashboardActivity.class));
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
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }
}
