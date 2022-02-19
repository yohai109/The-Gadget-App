package com.example.thegadgetapp.database;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesRepository {
    SharedPreferences sharedPreferences;

    public SharedPreferencesRepository(Context context) {
        sharedPreferences = context.getSharedPreferences("TheGadgetApp", Context.MODE_PRIVATE);
    }

    public void setCurrUser(String currUserId) {
        sharedPreferences.edit().putString("_id", currUserId).apply();
    }

    public String getCurrUserId() {
        return sharedPreferences.getString("_id", null);
    }
}
