package com.example.thegadgetapp.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.thegadgetapp.database.entities.Article;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert
    void insert(Article article);

    @Insert
    void insert(Article... article);

    @Query("SELECT * FROM Articles")
    LiveData<List<Article>> getAll();
}
