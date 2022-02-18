package com.example.thegadgetapp.createarticle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;
import com.example.thegadgetapp.activity.MainActivity;
import com.example.thegadgetapp.database.entities.Article;

import java.util.UUID;


public class CreateArticleFragment extends Fragment {
    EditText headerEdittext;
    EditText secondaryHeaderEdittext;
    EditText bodyEdittext;
    Button saveButton;
    CreateArticleViewModel viewModel;

    public CreateArticleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_article, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initViewModel();
        setSaveClick();
    }

    private void initViewModel() {
        ViewModelFactory factory = ((MainActivity)requireActivity()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(CreateArticleViewModel.class);
    }

    private void initViews(View view) {
        headerEdittext = view.findViewById(R.id.header_edittext);
        secondaryHeaderEdittext = view.findViewById(R.id.secondary_header_edittext);
        bodyEdittext = view.findViewById(R.id.body_edittext);
        saveButton = view.findViewById(R.id.save_button);
    }

    private void setSaveClick() {
        saveButton.setOnClickListener(v -> {
            String header = headerEdittext.getText().toString();
            String secondaryHeader = secondaryHeaderEdittext.getText().toString();
            String body = bodyEdittext.getText().toString();
            Article newArticle = new Article(
                    UUID.randomUUID().toString(),
                    ((MainActivity) requireActivity()).currUserId,
                    header,
                    secondaryHeader,
                    body
            );

            viewModel.saveArticle(newArticle);
        });
    }
}