package com.example.thegadgetapp.newsfeed;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thegadgetapp.database.entities.Article;

public class NewsViewHolder extends RecyclerView.ViewHolder{
    Article currArticle;


    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(Article newArticle){
        currArticle = newArticle;
    }
}
