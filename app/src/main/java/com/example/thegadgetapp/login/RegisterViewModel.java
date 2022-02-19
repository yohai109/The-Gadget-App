package com.example.thegadgetapp.login;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
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
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    public RegisterViewModel(GadgetDatabase localDB, FirebaseRepository remoteDB) {
        this.localDB = localDB;
        this.remoteDB = remoteDB;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void insertUserRemote(User user, FirebaseRepository.insertUserCallback callback) {
        executor.execute(() -> {
            remoteDB.insertUser(user, callback);
        });
    }

    public void insertUserLocal(User user, FirebaseRepository.insertUserCallback callback) {
        executor.execute(() -> {
            localDB.userDao().insert(user);
            mainThread.post(() -> {
                callback.onComplete(true);
            });
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
