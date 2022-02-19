package com.example.thegadgetapp.login;

import androidx.lifecycle.ViewModel;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.entities.User;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RegisterViewModel extends ViewModel {

    private GadgetDatabase localDB;
    private FirebaseRepository remoteDB;
    private Executor executor;

    public RegisterViewModel(GadgetDatabase localDB, FirebaseRepository remoteDB) {
        this.localDB = localDB;
        this.remoteDB = remoteDB;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void insert(User user) {
        executor.execute(() -> {
            localDB.userDao().insert(user);
            remoteDB.insert(user);
        });
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        this.localDB = null;
        this.remoteDB = null;
        this.executor = null;
    }
}
