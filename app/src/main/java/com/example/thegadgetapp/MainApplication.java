package com.example.thegadgetapp;

import android.app.Application;

import androidx.room.Room;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.entities.User;

import java.util.UUID;

public class MainApplication extends Application {
    private GadgetDatabase db;
    private FirebaseRepository firebase;
    private ViewModelFactory factory;


    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(
                getApplicationContext(),
                GadgetDatabase.class,
                "database-name"
        ).build();

        firebase = FirebaseRepository.getINSTANCE();

        factory = new ViewModelFactory(firebase, db);
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
}
