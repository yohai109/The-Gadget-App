package com.example.thegadgetapp.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.SharedPreferencesRepository;
import com.example.thegadgetapp.database.entities.User;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProfileViewModel extends ViewModel {
    private GadgetDatabase localDB;
    private FirebaseRepository remoteDB;
    private SharedPreferencesRepository currUserRepo;
    private Executor executor;

    public ProfileViewModel(GadgetDatabase localDB, FirebaseRepository remoteDB, SharedPreferencesRepository currUserRepo) {
        this.localDB = localDB;
        this.remoteDB = remoteDB;
        this.executor = Executors.newFixedThreadPool(2);
        this.currUserRepo = currUserRepo;
    }

    public LiveData<User> getCurrUser(){
        return localDB.userDao().getById(currUserRepo.getCurrUserId());
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        this.localDB = null;
        this.remoteDB = null;
        this.executor = null;
        this.currUserRepo = null;
    }
}
