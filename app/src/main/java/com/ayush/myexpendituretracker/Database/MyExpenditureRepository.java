package com.ayush.myexpendituretracker.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MyExpenditureRepository {

    private MyExpenditureDao myExpenditureDao;
    private LiveData<List<MyExpenditureModel>> allData;
    public MyExpenditureRepository(Application application) {

        MyExpenditureDatabase db = MyExpenditureDatabase.getDatabase(application);
        myExpenditureDao = db.myExpenditureDao();
        allData = myExpenditureDao.getDetails();
    }

    public LiveData<List<MyExpenditureModel>> getAllData() {
        return allData;
    }

    public void insertData(MyExpenditureModel data) {
        new DataInsertion(myExpenditureDao).execute(data);
    }

    private static class DataInsertion extends AsyncTask<MyExpenditureModel, Void, Void> {
        private MyExpenditureDao myExpenditureDao;

        private DataInsertion(MyExpenditureDao myexpendituredao) {
            this.myExpenditureDao = myexpendituredao;
        }

        @Override
        protected Void doInBackground(MyExpenditureModel... data) {
            myExpenditureDao.deleteAllData();
            myExpenditureDao.insertDetails(data[0]);
            return null;
        }
    }
}
