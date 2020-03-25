package com.ayush.myexpendituretracker.Database;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

@androidx.room.Entity(tableName = "MyExpenditureDetails")

public class MyExpenditureModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int id;

    @ColumnInfo(name = "Date")
    private String Date;

    @ColumnInfo(name = "MonthlyIncome")
    private String MonthlyIncome;

    @ColumnInfo(name = "Saving")
    private String Saving;

    @ColumnInfo(name = "Expenditure")
    private String Expenditure;

    // Creating Setter and Getter is important
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMonthlyIncome() {
        return MonthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        MonthlyIncome = monthlyIncome;
    }

    public String getSaving() {
        return Saving;
    }

    public void setSaving(String saving) {
        Saving = saving;
    }

    public String getExpenditure() {
        return Expenditure;
    }

    public void setExpenditure(String expenditure) {
        Expenditure = expenditure;
    }
}