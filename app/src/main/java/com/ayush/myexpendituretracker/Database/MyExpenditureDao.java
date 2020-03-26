package com.ayush.myexpendituretracker.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ayush.myexpendituretracker.DAO.LastMonthExpenditure;
import com.ayush.myexpendituretracker.DAO.TotalExpenditure;

import java.util.List;

@Dao
public interface MyExpenditureDao {

    @Insert
    void insertDetails(MyExpenditureModel data);

    @Query("SELECT * FROM MyExpenditureDetails WHERE month =:month")
    LiveData<List<MyExpenditureModel>> getCurrentMonthDetails(String month);

    @Query("SELECT * FROM MyExpenditureDetails WHERE month =:month")
    LiveData<List<LastMonthExpenditure>> getLastMonthDetails(String month);

    @Query("SELECT sum(Expenditure) as Expenditure FROM MyExpenditureDetails WHERE month =:month") //25/03/2020
    LiveData<List<TotalExpenditure>> getTotalExpenditure(String month);

    @Query("SELECT * FROM MyExpenditureDetails WHERE month = :month ORDER BY Expenditure DESC LIMIT 1")
    LiveData<List<MyExpenditureModel>> getTopExpenditure(String month);

    @Query("delete from MyExpenditureDetails")
    void deleteAllData();
}
