package com.example.thegadgetapp.database;

import android.net.Uri;

import com.example.thegadgetapp.database.entities.Article;
import com.example.thegadgetapp.database.entities.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class FirebaseRepository {
    static private FirebaseRepository INSTANCE = null;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;


    private FirebaseRepository() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
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

    public Task<QuerySnapshot> getAllArticles() {
        return db.collection("Articles").get();
    }

    public void uploadImage(byte[] data, onImageUploadComplete callback) {
        StorageReference ref = storageRef.child("images/" + UUID.randomUUID().toString());
        ref.putBytes(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ref.getDownloadUrl().addOnCompleteListener(task2 -> callback.onComplete(task2.getResult()));
            }
        });
    }

    public void insertUser(User user, insertUserCallback callback) {
        db.collection("Users")
                .whereEqualTo("username", user.username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            db.collection("Users").add(user);
                            callback.onComplete(true);
                        } else {
                            callback.onComplete(false);
                        }
                    }
                });
    }

    public void updateUser(User user) {
        db.collection("Users")
                .whereEqualTo("id", user.id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            doc.getReference().update(User.toMap(user));
                        }
                    }
                });
    }

    public void getUser(String username, getUserCallback callback) {
        db.collection("Users")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot res = task.getResult();
                        if (res == null || res.isEmpty()) {
                            callback.onComplete(null);
                        } else {
                            for (DocumentSnapshot doc : res) {
                                callback.onComplete(User.fromMap(doc));
                            }
                        }
                    }
                });
    }

    public void deleteArticle(Article article) {
        db.collection("Articles")
                .whereEqualTo("_id", article.id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot doc: task.getResult()){
                            doc.getReference().delete();
                        }
                    }
                });
    }

    public interface getUserCallback {
        void onComplete(User user);
    }

    public interface insertUserCallback {
        void onComplete(Boolean isSuccessful);
    }

    public interface onImageUploadComplete {
        void onComplete(Uri uri);
    }
}
