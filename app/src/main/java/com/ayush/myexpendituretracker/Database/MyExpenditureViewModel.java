package com.ayush.myexpendituretracker.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyExpenditureViewModel extends AndroidViewModel {

    private MyExpenditureRepository repository;
    private LiveData<List<MyExpenditureModel>> getAllData;

    public MyExpenditureViewModel(@NonNull Application application) {
        super(application);

        repository = new MyExpenditureRepository(application);
        getAllData = repository.getAllData();
    }

    public void insert(MyExpenditureModel data) {
        repository.insertData(data);
    }

    public LiveData<List<MyExpenditureModel>> getGetAllData() {
        return getAllData;
    }

}
