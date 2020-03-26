package com.ayush.myexpendituretracker.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ayush.myexpendituretracker.DAO.LastMonthExpenditure;
import com.ayush.myexpendituretracker.DAO.TotalExpenditure;

import java.util.List;

public class MyExpenditureRepository {

    private MyExpenditureDao myExpenditureDao;

    public MyExpenditureRepository(Application application) {
        MyExpenditureDatabase db = MyExpenditureDatabase.getDatabase(application);
        myExpenditureDao = db.myExpenditureDao();
    }

    LiveData<List<MyExpenditureModel>> getCurrentMonthDetails(String month) {
        return myExpenditureDao.getCurrentMonthDetails(month);
    }

    LiveData<List<LastMonthExpenditure>> getLastMonthDetails(String month) {
        return myExpenditureDao.getLastMonthDetails(month);
    }

    LiveData<List<TotalExpenditure>> getTotalExpenditure(String month) {
        return myExpenditureDao.getTotalExpenditure(month);
    }

    LiveData<List<MyExpenditureModel>> getTopExp(String month) {
        return myExpenditureDao.getTopExpenditure(month);
    }

    void insertData(MyExpenditureModel data) {
        new DataInsertion(myExpenditureDao).execute(data);
    }

    private static class DataInsertion extends AsyncTask<MyExpenditureModel, Void, Void> {
        private MyExpenditureDao myExpenditureDao;

        private DataInsertion(MyExpenditureDao myexpendituredao) {
            this.myExpenditureDao = myexpendituredao;
        }

        @Override
        protected Void doInBackground(MyExpenditureModel... data) {
            myExpenditureDao.insertDetails(data[0]);

            return null;
        }
    }

    void deleteTask() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myExpenditureDao.deleteAllData();
                return null;
            }
        }.execute();
    }
}
