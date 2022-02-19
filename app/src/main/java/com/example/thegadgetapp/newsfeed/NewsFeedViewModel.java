package com.example.thegadgetapp.newsfeed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.thegadgetapp.database.FirebaseRepository;
import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.entities.Article;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
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

    LiveData<List<Article>> getAllArticles() {
        return localDB.articleDao().getAll();
    }

    public void refreshFromRemote() {
//        executor.execute(() -> {
            remoteDB.getAllArticles().addOnCompleteListener(task -> {
                ArrayList<Article> newArticles = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (DocumentSnapshot currDoc : task.getResult()) {
                        newArticles.add(Article.fromMap(currDoc));
                    }
                    executor.execute(() -> {
                        localDB.articleDao().insert(newArticles.toArray(new Article[0]));
                    });
                }
            });
//        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.localDB = null;
        this.remoteDB = null;
        this.executor = null;
    }
}
