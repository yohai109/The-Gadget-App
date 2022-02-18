package com.example.thegadgetapp.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;

import com.example.thegadgetapp.database.entities.Article;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert
    void insert(Article article);

    @Insert
    void insert(Article... article);

    LiveData<List<Article>> getAll();
}
