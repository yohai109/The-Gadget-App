package com.example.thegadgetapp.newsfeed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.entities.Article;
import com.example.thegadgetapp.database.entities.User;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NewsFeedViewModel extends ViewModel {
    private GadgetDatabase localDB;
    private FirebaseRepository remoteDB;
    private Executor executor;

    public NewsFeedViewModel(GadgetDatabase localDB, FirebaseRepository remoteDB) {
        this.localDB = localDB;
        this.remoteDB = remoteDB;
        this.executor = Executors.newFixedThreadPool(2);
    }

    LiveData<List<Article>> getAllArticles(){
        return localDB.articleDao().getAll();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.localDB = null;
        this.remoteDB = null;
        this.executor = null;
    }
}
