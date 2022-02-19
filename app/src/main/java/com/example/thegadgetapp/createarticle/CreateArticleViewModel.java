package com.example.thegadgetapp.createarticle;

import androidx.lifecycle.ViewModel;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.entities.Article;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CreateArticleViewModel extends ViewModel {

    private GadgetDatabase localDB;
    private FirebaseRepository remoteDB;
    private Executor executor;

    public CreateArticleViewModel(GadgetDatabase localDB, FirebaseRepository remoteDB) {
        this.localDB = localDB;
        this.remoteDB = remoteDB;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void saveArticle(Article newArticle) {
        executor.execute(() -> {
            localDB.articleDao().insert(newArticle);
            remoteDB.saveArticle(newArticle);
        });
    }

    public void uploadImage(byte[] data, FirebaseRepository.onImageUploadComplete callback) {
        remoteDB.uploadImage(data, callback);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.localDB = null;
        this.remoteDB = null;
        this.executor = null;
    }
}
