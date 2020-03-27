package com.ayush.myexpendituretracker.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ayush.myexpendituretracker.DAO.MyExpenditureModel;
import com.ayush.myexpendituretracker.Interface.MyExpenditureDao;

@Database(entities = {MyExpenditureModel.class}, version = 1, exportSchema = false)
public abstract class MyExpenditureDatabase extends RoomDatabase {

    public abstract MyExpenditureDao myExpenditureDao();
    private static MyExpenditureDatabase INSTANCE;
    public static MyExpenditureDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (MyExpenditureDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context, MyExpenditureDatabase.class, "MY_EXPENDITURE_DATABASE")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}