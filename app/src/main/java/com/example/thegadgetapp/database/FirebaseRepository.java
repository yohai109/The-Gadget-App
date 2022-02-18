package com.example.thegadgetapp.database;

import com.example.thegadgetapp.database.entities.Article;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirebaseRepository {
    static private FirebaseRepository INSTANCE = null;
    FirebaseFirestore db;

    private FirebaseRepository() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }

    public static FirebaseRepository getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new FirebaseRepository();
        }
        return INSTANCE;
    }

    public void saveArticle(Article newArticle) {
        db.collection("Articles").add(Article.toMap(newArticle));
    }
}
