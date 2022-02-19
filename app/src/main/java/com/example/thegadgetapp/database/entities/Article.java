package com.example.thegadgetapp.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

@Entity(
        tableName = "Articles"//,
//        foreignKeys = {@ForeignKey(
//                entity = User.class,
//                parentColumns = "id",
//                childColumns = "creatorId",
//                onDelete = ForeignKey.CASCADE
//        )}
)
public class Article {
    @PrimaryKey
    @NonNull
    public String id;

    @NonNull
    public String creatorId;

    @NonNull
    public String header;

    @NonNull
    public String secondaryHeader;

    @NonNull
    public String body;

    public String imageUri;

    public Article(
            @NonNull String id,
            @NonNull String creatorId,
            @NonNull String header,
            @NonNull String secondaryHeader,
            @NonNull String body,
            String imageUri
    ) {
        this.id = id;
        this.creatorId = creatorId;
        this.header = header;
        this.secondaryHeader = secondaryHeader;
        this.body = body;
        this.imageUri = imageUri;
    }



    public static Map<String, Object> toMap(Article article) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_id", article.id);
        map.put("creatorId", article.creatorId);
        map.put("header", article.header);
        map.put("secondaryHeader", article.secondaryHeader);
        map.put("body", article.body);
        map.put("imageUri", article.imageUri);
        return map;
    }

    public static Article fromMap(DocumentSnapshot map) {
        return new Article(
                (String) map.get("_id"),
                (String) map.get("creatorId"),
                (String) map.get("header"),
                (String) map.get("secondaryHeader"),
                (String) map.get("body"),
                (String) map.get("imageUri")
        );
    }
}
