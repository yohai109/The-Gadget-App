package com.example.thegadgetapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.thegadgetapp.activity.ActivityViewModel;
import com.example.thegadgetapp.createarticle.CreateArticleViewModel;
import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.SharedPreferencesRepository;
import com.example.thegadgetapp.details.DetailsViewModel;
import com.example.thegadgetapp.editarticle.EditArticleViewModel;
import com.example.thegadgetapp.editprofile.EditProfileViewModel;
import com.example.thegadgetapp.login.LoginViewModel;
import com.example.thegadgetapp.newsfeed.NewsFeedViewModel;
import com.example.thegadgetapp.profile.ProfileViewModel;
import com.example.thegadgetapp.registration.RegisterViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private FirebaseRepository firebaseRepository;
    private GadgetDatabase gadgetDatabase;
    private SharedPreferencesRepository sharedPreferencesRepository;

    public ViewModelFactory(
            FirebaseRepository firebaseRepository,
            GadgetDatabase gadgetDatabase,
            SharedPreferencesRepository sharedPreferencesRepository
    ) {
        this.firebaseRepository = firebaseRepository;
        this.gadgetDatabase = gadgetDatabase;
        this.sharedPreferencesRepository = sharedPreferencesRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(LoginViewModel.class)) {
            return (T) new LoginViewModel(gadgetDatabase, firebaseRepository, sharedPreferencesRepository);
        } else if (modelClass.equals(ActivityViewModel.class)) {
            return (T) new ActivityViewModel(gadgetDatabase, firebaseRepository, sharedPreferencesRepository);
        } else if (modelClass.equals(NewsFeedViewModel.class)) {
            return (T) new NewsFeedViewModel(gadgetDatabase, firebaseRepository);
        } else if (modelClass.equals(CreateArticleViewModel.class)) {
            return (T) new CreateArticleViewModel(gadgetDatabase, firebaseRepository);
        } else if (modelClass.equals(DetailsViewModel.class)) {
            return (T) new DetailsViewModel(gadgetDatabase, sharedPreferencesRepository);
        } else if (modelClass.equals(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(gadgetDatabase, firebaseRepository);
        } else if (modelClass.equals(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(gadgetDatabase, firebaseRepository, sharedPreferencesRepository);
        } else if (modelClass.equals(EditProfileViewModel.class)) {
            return (T) new EditProfileViewModel(gadgetDatabase, firebaseRepository, sharedPreferencesRepository);
        } else if (modelClass.equals(EditArticleViewModel.class)) {
            return (T) new EditArticleViewModel(gadgetDatabase, firebaseRepository);
        }
        return null;
    }
}
