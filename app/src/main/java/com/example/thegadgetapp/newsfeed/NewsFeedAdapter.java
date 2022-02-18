package com.example.thegadgetapp.newsfeed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.database.entities.Article;

import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private List<Article> data = null;

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    public void setData(List<Article> newData) {
        data = newData;
        notifyDataSetChanged();
    }
}
