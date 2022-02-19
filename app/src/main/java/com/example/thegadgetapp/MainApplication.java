package com.example.thegadgetapp;

import android.app.Application;

import androidx.room.Room;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.SharedPreferencesRepository;

public class MainApplication extends Application {
    private GadgetDatabase db;
    private FirebaseRepository firebase;
    private ViewModelFactory factory;
    private SharedPreferencesRepository sharedPreferencesRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(
                getApplicationContext(),
                GadgetDatabase.class,
                "database-name"
        ).build();

        firebase = FirebaseRepository.getINSTANCE();

        sharedPreferencesRepository = new SharedPreferencesRepository(this);

        factory = new ViewModelFactory(firebase, db, sharedPreferencesRepository);
    }

    public GadgetDatabase getDatabase() {
        return db;
    }

    public FirebaseRepository getFirebase() {
        return firebase;
    }

    public ViewModelFactory getFactory() {
        return factory;
    }

    public SharedPreferencesRepository getSharedPreferencesRepository() {
        return sharedPreferencesRepository;
    }
}
