package com.example.thegadgetapp.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.DocumentSnapshot;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    public String id;
    @NonNull
    public String username;
    @NonNull
    public String password;

    public User(@NonNull String id, @NonNull String username, @NonNull String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static User fromMap(DocumentSnapshot map) {
        return new User(
                (String) map.get("id"),
                (String) map.get("username"),
                (String) map.get("password")
        );
    }
}
