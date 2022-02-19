package com.example.thegadgetapp.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.thegadgetapp.database.entities.User;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE username=:username LIMIT 1")
    User getByUsername(String username);

    @Query("SELECT * FROM users WHERE id is :id LIMIT 1")
    LiveData<User> getById(String id);
}
