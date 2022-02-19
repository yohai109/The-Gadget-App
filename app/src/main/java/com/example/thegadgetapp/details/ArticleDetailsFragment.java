package com.example.thegadgetapp.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;
import com.example.thegadgetapp.activity.MainActivity;

public class ArticleDetailsFragment extends Fragment {

    DetailsViewModel viewModel;
    String articleID;

    TextView titleTextView;
    TextView secondaryHeaderTextView;
    TextView bodyTextView;

    public ArticleDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        articleID = ArticleDetailsFragmentArgs.fromBundle(getArguments()).getArticleId();

        titleTextView = view.findViewById(R.id.title_text);
        secondaryHeaderTextView = view.findViewById(R.id.secondary_header_text);
        bodyTextView = view.findViewById(R.id.body_tex);

        ViewModelFactory factory = ((MainActivity) requireActivity()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(DetailsViewModel.class);
        viewModel.getArticle(articleID).observe(getViewLifecycleOwner(), article -> {
            if (article != null) {
                titleTextView.setText(article.header);
                secondaryHeaderTextView.setText(article.secondaryHeader);
                bodyTextView.setText(article.body);
            }
        });
    }
}