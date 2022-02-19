package com.example.thegadgetapp.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.thegadgetapp.database.GadgetDatabase;
import com.example.thegadgetapp.database.SharedPreferencesRepository;
import com.example.thegadgetapp.database.entities.Article;

public class DetailsViewModel extends ViewModel {
    private GadgetDatabase localDB;
    private SharedPreferencesRepository currUserRepo;

    public DetailsViewModel(GadgetDatabase localDB, SharedPreferencesRepository currUserRepo) {
        this.localDB = localDB;
        this.currUserRepo = currUserRepo;
    }

    public LiveData<Article> getArticle(String Id) {
        return localDB.articleDao().getById(Id);
    }

    public String getCurrUserId() {
        return currUserRepo.getCurrUserId();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.localDB = null;
        this.currUserRepo = null;
    }
}
