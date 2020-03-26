package com.ayush.myexpendituretracker.DAO;

import androidx.room.ColumnInfo;

public class TotalExpenditure {

    @ColumnInfo(name = "Expenditure")
    public String TotalExpenditure;

    public String getTotalExpenditure() {
        return TotalExpenditure;
    }

    public void setTotalExpenditure(String totalExpenditure) {
        TotalExpenditure = totalExpenditure;
    }
}
