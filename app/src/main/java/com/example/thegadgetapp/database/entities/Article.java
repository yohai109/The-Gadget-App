package com.example.thegadgetapp.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity(
        tableName = "Articles",
        foreignKeys = {@ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "creatorId",
                onDelete = ForeignKey.CASCADE
        )}
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

    public Article(@NonNull String id, @NonNull String creatorId, @NonNull String header, @NonNull String secondaryHeader, @NonNull String body) {
        this.id = id;
        this.creatorId = creatorId;
        this.header = header;
        this.secondaryHeader = secondaryHeader;
        this.body = body;
    }

    public static Map<String,Object> toMap(Article article){
        HashMap<String,Object> map = new HashMap<>();
        map.put("_id", article.id);
        map.put("creatorId", article.creatorId);
        map.put("header", article.header);
        map.put("secondaryHeader", article.secondaryHeader);
        map.put("body", article.body);
        return map;
    }
}
