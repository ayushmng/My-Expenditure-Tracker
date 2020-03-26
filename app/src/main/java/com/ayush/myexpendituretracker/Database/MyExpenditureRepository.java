package com.ayush.myexpendituretracker.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ayush.myexpendituretracker.DAO.LastMonthExpenditure;
import com.ayush.myexpendituretracker.DAO.TotalExpenditure;

import java.util.List;

public class MyExpenditureRepository {

    private MyExpenditureDao myExpenditureDao;
//    private LiveData<List<MyExpenditureModel>> allData;
    private LiveData<List<MyExpenditureModel>> getTopExp;
    private LiveData<List<MyExpenditureModel>> getCurrentMonthDetails;
    private LiveData<List<MyExpenditureModel>> getLastMonthDetails;
    private LiveData<List<TotalExpenditure>> getTotalExpenditure;

    public MyExpenditureRepository(Application application) {

        MyExpenditureDatabase db = MyExpenditureDatabase.getDatabase(application);
        myExpenditureDao = db.myExpenditureDao();
//        allData = myExpenditureDao.getDetails();
//        getTopExp = myExpenditureDao.getTopExpenditure();
    }

//    public LiveData<List<MyExpenditureModel>> getAllData() {
//        return allData;
//    }

    public LiveData<List<MyExpenditureModel>> getCurrentMonthDetails(String month) {
        return myExpenditureDao.getCurrentMonthDetails(month);
    }

    public LiveData<List<LastMonthExpenditure>> getLastMonthDetails(String month) {
        return myExpenditureDao.getLastMonthDetails(month);
    }

    public LiveData<List<TotalExpenditure>> getTotalExpenditure(String month) {
        return myExpenditureDao.getTotalExpenditure(month);
    }

    public LiveData<List<MyExpenditureModel>> getTopExp(String month) {
        return myExpenditureDao.getTopExpenditure(month);
    }

    public void insertData(MyExpenditureModel data) {
        new DataInsertion(myExpenditureDao).execute(data);
    }

    /*public void insertLastMonthData(LastMonthExpenditure lastMonthExpenditure){
        new DataInsertion(lastMonthExpenditure);
    }*/

    private static class DataInsertion extends AsyncTask<MyExpenditureModel, Void, Void> {
        private MyExpenditureDao myExpenditureDao;
        private LastMonthExpenditure lastMonthExpenditure;

        private DataInsertion(MyExpenditureDao myexpendituredao) {
            this.myExpenditureDao = myexpendituredao;
        }

       /* private DataInsertion(LastMonthExpenditure lastMonthExpenditure){
            this.lastMonthExpenditure = lastMonthExpenditure;
        }*/

        @Override
        protected Void doInBackground(MyExpenditureModel... data) {
//            myExpenditureDao.deleteAllData();
            myExpenditureDao.insertDetails(data[0]);
            return null;
        }
    }
}
