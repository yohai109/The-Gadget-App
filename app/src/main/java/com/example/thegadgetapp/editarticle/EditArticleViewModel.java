package com.example.thegadgetapp.editarticle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.entities.Article;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditArticleViewModel extends ViewModel {

    private GadgetDatabase localDB;
    private FirebaseRepository remoteDB;
    private Executor executor;

    public EditArticleViewModel(GadgetDatabase localDB, FirebaseRepository remoteDB) {
        this.localDB = localDB;
        this.remoteDB = remoteDB;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void saveArticle(Article newArticle) {
        executor.execute(() -> {
            remoteDB.saveArticle(newArticle);
        });
    }

    public void update(
            String articleId,
            String creatorId,
            String header,
            String secondaryHeader,
            String body,
            String imgUrl
    ){
        executor.execute(() -> {
            localDB.articleDao().update(
                    new Article(articleId,
                            creatorId,
                            header,
                            secondaryHeader,
                            body,
                            imgUrl)
            );
        });
    }


    public void uploadImage(byte[] data, FirebaseRepository.onImageUploadComplete callback) {
        remoteDB.uploadImage(data, callback);
    }

    public LiveData<Article> getArticle(String Id) {
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
