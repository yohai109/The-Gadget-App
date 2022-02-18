package com.example.thegadgetapp.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Articles")
public class Article {
    @PrimaryKey
    @NonNull
    public String id;

    @NonNull
    public String header;

    @NonNull
    public String secondaryHeader;

    @NonNull
    public String body;

    public Article(@NonNull String id, @NonNull String header, @NonNull String secondaryHeader, @NonNull String body) {
        this.id = id;
        this.header = header;
        this.secondaryHeader = secondaryHeader;
        this.body = body;
    }
}
