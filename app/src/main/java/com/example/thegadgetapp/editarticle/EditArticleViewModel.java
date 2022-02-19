package com.example.thegadgetapp.editarticle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.entities.Article;

import java.util.concurrent.Executor;

public class EditArticleViewModel extends ViewModel {

    private GadgetDatabase localDB;
    private FirebaseRepository remoteDB;
    private Executor executor;

    public EditArticleViewModel(GadgetDatabase localDB, FirebaseRepository remoteDB) {
        this.localDB = localDB;
        this.remoteDB = remoteDB;
    }

    public void saveArticle(Article newArticle) {
        executor.execute(() -> {
            localDB.articleDao().insert(newArticle);
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
    ) {
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

    public void deleteArticle(String id) {
        executor.execute(() -> {
            Article article = new Article(
                    id,
                    "",
                    "",
                    "",
                    "",
                    ""
            );

            remoteDB.deleteArticle(article);
            localDB.articleDao().delete(article);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.localDB = null;
        this.remoteDB = null;
        this.executor = null;
    }
}
