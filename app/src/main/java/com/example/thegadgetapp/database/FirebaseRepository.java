package com.example.thegadgetapp.database;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseRepository {
    static private FirebaseRepository INSTANCE = null;
    FirebaseFirestore db;

    private FirebaseRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public static FirebaseRepository getINSTANCE() {
        if (INSTANCE == null){
            INSTANCE = new FirebaseRepository();
        }
        return INSTANCE;
    }
}
