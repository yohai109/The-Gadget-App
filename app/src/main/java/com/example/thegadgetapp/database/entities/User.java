package com.example.thegadgetapp.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    public String id;

    @NonNull
    public String username;

    @NonNull
    public String name;

    @NonNull
    public String password;

    public String avatarUri;

    public User(@NonNull String id, @NonNull String username, @NonNull String password, String avatarUri, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.avatarUri = avatarUri;
        this.name = name;
    }

    public static User fromMap(DocumentSnapshot map) {
        return new User(
                (String) map.get("id"),
                (String) map.get("username"),
                (String) map.get("password"),
                (String) map.get("avatarUri"),
                (String) map.get("name")
        );
    }

    public static Map<String, Object> toMap(User user) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", user.id);
        map.put("username", user.username);
        map.put("password", user.password);
        map.put("avatarUri", user.avatarUri);
        map.put("name", user.name);

        return map;
    }
}
