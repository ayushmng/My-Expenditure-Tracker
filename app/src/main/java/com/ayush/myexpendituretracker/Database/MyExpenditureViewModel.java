package com.ayush.myexpendituretracker.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ayush.myexpendituretracker.DAO.LastMonthExpenditure;
import com.ayush.myexpendituretracker.DAO.TotalExpenditure;

import java.util.List;

public class MyExpenditureViewModel extends AndroidViewModel {

    private MyExpenditureRepository repository;

    public MyExpenditureViewModel(@NonNull Application application) {
        super(application);
        repository = new MyExpenditureRepository(application);
    }

    public void insert(MyExpenditureModel data) {
        repository.insertData(data);
    }

    public void delete(){
        repository.deleteTask();
    }

    public LiveData<List<MyExpenditureModel>> getCurrentMonthDetails(String month) {
        return repository.getCurrentMonthDetails(month);
    }

    public LiveData<List<LastMonthExpenditure>> getLastMonthDetails(String month) {
        return repository.getLastMonthDetails(month);
    }

    public LiveData<List<TotalExpenditure>> getTotalExpenditure(String month) {
        return repository.getTotalExpenditure(month);
    }

    public LiveData<List<MyExpenditureModel>> getTopExp(String month) {
        return repository.getTopExp(month);
    }
}
