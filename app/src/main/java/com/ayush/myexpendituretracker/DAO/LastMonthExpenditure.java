package com.ayush.myexpendituretracker.DAO;

import androidx.room.ColumnInfo;

public class LastMonthExpenditure {

    @ColumnInfo(name = "Id")
    private int id;

    @ColumnInfo(name = "Date")
    private String Date;

    @ColumnInfo(name = "Title")
    private String Title;

    @ColumnInfo(name = "Month")
    private String Month;

    @ColumnInfo(name = "Expenditure")
    private String Expenditure;

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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getExpenditure() {
        return Expenditure;
    }

    public void setExpenditure(String expenditure) {
        Expenditure = expenditure;
    }
}
