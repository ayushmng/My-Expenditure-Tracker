package com.ayush.myexpendituretracker.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyExpenditureDao {

    @Insert
    void insertDetails(MyExpenditureModel data);

    @Query("select * from MyExpenditureDetails")
    LiveData<List<MyExpenditureModel>> getDetails();

    @Query("delete from MyExpenditureDetails")
    void deleteAllData();
}
