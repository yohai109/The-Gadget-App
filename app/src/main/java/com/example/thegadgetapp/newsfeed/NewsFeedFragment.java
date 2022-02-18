package com.example.thegadgetapp.newsfeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;
import com.example.thegadgetapp.activity.MainActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class NewsFeedFragment extends Fragment {

    RecyclerView newsFeed;
    NewsFeedAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ExtendedFloatingActionButton fab;

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
        initViewModel();
        initFab();
        initRefresh();
        initAdapter();
        observeDB();
    }

    private void findViews(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        newsFeed = view.findViewById(R.id.news_feed_recycler);
        adapter = new NewsFeedAdapter();
        fab = view.findViewById(R.id.news_feed_fab);
    }

    private void initViewModel() {
        ViewModelFactory factory = ((MainActivity) requireActivity()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(NewsFeedViewModel.class);
    }

    private void initFab() {
        fab.setOnClickListener(v -> Navigation.findNavController(v).navigate(
                NewsFeedFragmentDirections.actionNewsFeedFragmentToCreateArticleFragment()
        ));
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        newsFeed.setLayoutManager(linearLayoutManager);
        newsFeed.setAdapter(adapter);
    }

    private void initRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            // TODO add firebase data fetching here
        });
    }


    private void observeDB() {
        viewModel.getAllArticles().observe(getViewLifecycleOwner(), articles -> {
            adapter.setData(articles);
        });
    }
}