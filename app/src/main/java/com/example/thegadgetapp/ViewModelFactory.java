package com.example.thegadgetapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.thegadgetapp.activity.ActivityViewModel;
import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.login.LoginViewModel;
import com.example.thegadgetapp.newsfeed.NewsFeedViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private FirebaseRepository firebaseRepository;
    private GadgetDatabase gadgetDatabase;

    public ViewModelFactory(FirebaseRepository firebaseRepository, GadgetDatabase gadgetDatabase) {
        this.firebaseRepository = firebaseRepository;
        this.gadgetDatabase = gadgetDatabase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.equals(LoginViewModel.class)) {
            return (T) new LoginViewModel(gadgetDatabase, firebaseRepository);
        } else if (modelClass.equals(ActivityViewModel.class)) {
            return (T) new ActivityViewModel(gadgetDatabase, firebaseRepository);
        } else if (modelClass.equals(NewsFeedViewModel.class)) {
            return (T) new NewsFeedViewModel(gadgetDatabase, firebaseRepository);
        }
        return null;
    }
}
