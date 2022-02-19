package com.example.thegadgetapp.login;

import androidx.lifecycle.ViewModel;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.SharedPreferencesRepository;
import com.example.thegadgetapp.database.entities.User;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginViewModel extends ViewModel {
    private GadgetDatabase localDB;
    private FirebaseRepository remoteDB;
    private SharedPreferencesRepository currUserRepo;
    private Executor executor;

    public LoginViewModel(GadgetDatabase localDB, FirebaseRepository remoteDB, SharedPreferencesRepository currUserRepo) {
        this.localDB = localDB;
        this.remoteDB = remoteDB;
        this.currUserRepo = currUserRepo;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void tryLogin(String username, String password, LoginCallback callback) {
        executor.execute(() -> {
            remoteDB.getUser(username, user -> {
                executor.execute(() -> {
                    localDB.userDao().insert(user);
                });
                callback.login(user != null && user.password.equals(password), user);
            });
        });
    }

    public void setCurrLoginUser(String id) {
        currUserRepo.setCurrUser(id);
    }

    public String getCurrUserId() {
        return currUserRepo.getCurrUserId();
    }

    public interface LoginCallback {
        void login(Boolean isSuccess, User user);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.localDB = null;
        this.remoteDB = null;
        this.executor = null;
        currUserRepo = null;
    }
}
