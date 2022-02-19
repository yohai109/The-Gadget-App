package com.example.thegadgetapp.newsfeed;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.database.entities.Article;
import com.squareup.picasso.Picasso;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    Article currArticle;
    TextView header;
    TextView secondaryHeader;
    ImageView imageView;

    public NewsViewHolder(@NonNull View itemView, ViewHolderOnClickListener clickListener) {
        super(itemView);

        header = itemView.findViewById(R.id.header);
        secondaryHeader = itemView.findViewById(R.id.secondary_header);
        imageView = itemView.findViewById(R.id.image);
        itemView.setOnClickListener(v -> {
            clickListener.onclick(itemView, currArticle);
        });
    }

    public void bind(Article newArticle) {
        currArticle = newArticle;
        header.setText(currArticle.header);
        secondaryHeader.setText(currArticle.secondaryHeader);
        Picasso.get()
                .load(currArticle.imageUri)
                .resize(120, 120)
                .centerCrop()
                .into(imageView);
    }

    public interface ViewHolderOnClickListener {
        void onclick(View view, Article article);
    }
}
