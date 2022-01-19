package com.example.thegadgetapp;

import android.app.Application;

import androidx.room.Room;

import com.example.thegadgetapp.database.GadgetDatabase;

public class MainApplication extends Application {
    private GadgetDatabase db;


    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(
                getApplicationContext(),
                GadgetDatabase.class,
                "database-name"
        ).build();
    }

    public GadgetDatabase getDatabase() {
        return db;
    }
}
