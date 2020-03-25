package com.ayush.myexpendituretracker.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ayush.myexpendituretracker.Database.MyExpenditureModel;
import com.ayush.myexpendituretracker.Database.MyExpenditureViewModel;
import com.ayush.myexpendituretracker.R;
import com.ayush.myexpendituretracker.SharedPreference.MySharedPreferences;

import java.util.List;

public class HomeFragment extends Fragment {

    private MyExpenditureViewModel myExpenditureViewModel;
    private HomeViewModel homeViewModel;
    private MySharedPreferences mySharedPreferences;

    //    Context context;
    private ImageButton edit;
    private TextView monthlyIncome, expectedSaving;
    private String MI, ES;

   /* public HomeFragment(Context context) {
        this.context = context;
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        edit = root.findViewById(R.id.edit_button);
        monthlyIncome = root.findViewById(R.id.monthly_income);
        expectedSaving = root.findViewById(R.id.expected_saving);
        mySharedPreferences = new MySharedPreferences(getActivity());

        MI = mySharedPreferences.getMonthlyincome();
        ES = mySharedPreferences.getSaving();

        if (!MI.isEmpty() && !ES.isEmpty()) {
            monthlyIncome.setText("Your Monthly Income: " + MI);
            expectedSaving.setText("Your Expected Saving: " + ES);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValues();
            }
        });

        myExpenditureViewModel = ViewModelProviders.of(this).get(MyExpenditureViewModel.class);
        myExpenditureViewModel.getGetAllData().observe(getViewLifecycleOwner(), new Observer<List<MyExpenditureModel>>() {
            @Override
            public void onChanged(@Nullable List<MyExpenditureModel> data) {
                try {
//                    email.setText((Objects.requireNonNull(data).get(0).getEmail()));
//                    password.setText((Objects.requireNonNull(data.get(0).getPassword())));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return root;

        /*homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });*/

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strEmail = edtemail.getText().toString().trim();
                String strPassword = edtpass.getText().toString().trim();

                LoginTable data = new LoginTable();

                if (TextUtils.isEmpty(strEmail)) {
                    edtemail.setError("Please Enter Your E-mail Address");
                }
                else if (TextUtils.isEmpty(strPassword)) {
                    edtpass.setError("Please Enter Your Password");
                }
                else {
                    data.setEmail(strEmail);
                    data.setPassword(strPassword);
                    loginViewModel.insert(data);

                }
            }
        });*/

    }

    private void setValues() {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.amount_holder, null);
        final EditText monthly_income = view.findViewById(R.id.edt_monthly_income);
        final EditText expected_saving = view.findViewById(R.id.edt_expected_salary);

        mBuilder.setCancelable(false);
        mBuilder.setView(view);
        mBuilder.setTitle("My Expenditure Tracker");

        MI = mySharedPreferences.getMonthlyincome();
        ES = mySharedPreferences.getSaving();

        if (!MI.isEmpty() && !ES.isEmpty()){
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
                    Toast.makeText(getActivity(), "Please provide amount", Toast.LENGTH_SHORT).show();
                } else {
                    if (Integer.parseInt(es) > Integer.parseInt(mi)) {
                        Toast.makeText(getActivity(), "Sorry, your Monthly Income can't be greater than Saving", Toast.LENGTH_SHORT).show();
                    } else {
                        mySharedPreferences.setMonthlyincome(mi);
                        mySharedPreferences.setSaving(es);
                        Toast.makeText(getActivity(), "Amount " + mi + " as monthly income and " + es + " as expected saving has been added to your account", Toast.LENGTH_LONG).show();

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
}
