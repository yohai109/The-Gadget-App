package com.example.thegadgetapp.newsfeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;
import com.example.thegadgetapp.activity.MainActivity;

public class NewsFeedFragment extends Fragment {

    RecyclerView newsFeed;
    NewsFeedAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    String currUserId;
    NewsFeedViewModel viewModel;

    public NewsFeedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currUserId = NewsFeedFragmentArgs.fromBundle(getArguments()).getUserId();
        findViews(view);

        ViewModelFactory factory = ((MainActivity) requireActivity()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(NewsFeedViewModel.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        newsFeed.setLayoutManager(linearLayoutManager);
        newsFeed.setAdapter(adapter);

        observeDB();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void findViews(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        newsFeed = view.findViewById(R.id.news_feed_recycler);
        adapter = new NewsFeedAdapter();
    }

    private void observeDB() {
        viewModel.getAllArticles().observe(getViewLifecycleOwner(), articles -> {
            adapter.setData(articles);
        });
    }
}