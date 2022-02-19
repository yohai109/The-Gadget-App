package com.example.thegadgetapp.database.daos;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.thegadgetapp.database.entities.Article;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert(onConflict = REPLACE)
    void insert(Article article);

    @Insert(onConflict = REPLACE)
    void insert(Article... article);

    @Update
    void update(Article... article);

    @Query("SELECT * FROM Articles")
    LiveData<List<Article>> getAll();

    @Query("SELECT * FROM Articles where id is :id")
    LiveData<Article> getById(String id);
}
