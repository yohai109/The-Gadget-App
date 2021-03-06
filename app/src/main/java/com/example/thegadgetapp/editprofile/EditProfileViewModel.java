package com.example.thegadgetapp.editprofile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.SharedPreferencesRepository;
import com.example.thegadgetapp.database.entities.User;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditProfileViewModel extends ViewModel {

    private GadgetDatabase localDB;
    private FirebaseRepository remoteDB;
    private SharedPreferencesRepository currUserRepo;
    private Executor executor;

    public EditProfileViewModel(GadgetDatabase localDB, FirebaseRepository remoteDB, SharedPreferencesRepository currUserRepo) {
        this.localDB = localDB;
        this.remoteDB = remoteDB;
        this.executor = Executors.newFixedThreadPool(2);
        this.currUserRepo = currUserRepo;
    }

    public LiveData<User> getCurrUser() {
        return localDB.userDao().getById(currUserRepo.getCurrUserId());
    }

    public void updateUser(User user) {
        executor.execute(() -> {
            localDB.userDao().update(user);
            remoteDB.updateUser(user);
        });

    }

    public void uploadImage(byte[] data, FirebaseRepository.onImageUploadComplete callback) {
        remoteDB.uploadImage(data, callback);
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
