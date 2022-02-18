package com.example.thegadgetapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.thegadgetapp.database.daos.ArticleDao;
import com.example.thegadgetapp.database.daos.UserDao;
import com.example.thegadgetapp.database.entities.Article;
import com.example.thegadgetapp.database.entities.User;

@Database(
        version = 1,
        entities = {User.class, Article.class}
)
public abstract class GadgetDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract ArticleDao articleDao();
}
