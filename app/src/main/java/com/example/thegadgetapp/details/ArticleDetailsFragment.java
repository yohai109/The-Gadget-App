package com.example.thegadgetapp.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;
import com.example.thegadgetapp.activity.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class ArticleDetailsFragment extends Fragment {

    private DetailsViewModel viewModel;
    private String articleID;
    private String currUserId;

    private TextView titleTextView;
    private TextView secondaryHeaderTextView;
    private TextView bodyTextView;
    private ImageView imageView;
    private FloatingActionButton fab;

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
        imageView = view.findViewById(R.id.image_imageview);
        fab = view.findViewById(R.id.edit_article_fab);

        ViewModelFactory factory = ((MainActivity) requireActivity()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(DetailsViewModel.class);
        currUserId = viewModel.getCurrUserId();
        viewModel.getArticle(articleID).observe(getViewLifecycleOwner(), article -> {
            if (article != null) {
                titleTextView.setText(article.header);
                secondaryHeaderTextView.setText(article.secondaryHeader);
                bodyTextView.setText(article.body);
                Picasso.get()
                        .load(article.imageUri)
                        .into(imageView);

                if (!currUserId.equals(article.creatorId)) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });
    }
}