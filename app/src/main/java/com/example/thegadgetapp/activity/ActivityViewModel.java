package com.example.thegadgetapp.activity;

import androidx.lifecycle.ViewModel;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.entities.User;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ActivityViewModel extends ViewModel {
    private GadgetDatabase localDB;
    private FirebaseRepository remoteDB;
    private Executor executor;

    public ActivityViewModel(GadgetDatabase localDB, FirebaseRepository remoteDB) {
        this.localDB = localDB;
        this.remoteDB = remoteDB;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void initDB() {
        User user = new User(UUID.randomUUID().toString(), "yohai", "123");
        executor.execute(() -> localDB.userDao().insert(user));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.localDB = null;
        this.remoteDB = null;
        this.executor = null;
    }
}
