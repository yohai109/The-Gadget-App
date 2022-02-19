package com.example.thegadgetapp.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.entities.Article;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailsViewModel extends ViewModel {
    private GadgetDatabase localDB;
    private FirebaseRepository remoteDB;
    private Executor executor;

    public DetailsViewModel(GadgetDatabase localDB, FirebaseRepository remoteDB) {
        this.localDB = localDB;
        this.remoteDB = remoteDB;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public LiveData<Article> getArticle(String Id){
        return localDB.articleDao().getById(Id);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.localDB = null;
        this.remoteDB = null;
        this.executor = null;
    }
}
