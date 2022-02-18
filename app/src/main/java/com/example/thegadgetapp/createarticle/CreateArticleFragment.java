package com.example.thegadgetapp.createarticle;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;
import com.example.thegadgetapp.activity.MainActivity;
import com.example.thegadgetapp.database.entities.Article;

import java.io.IOException;
import java.util.UUID;


public class CreateArticleFragment extends Fragment {
    private EditText headerEdittext;
    private EditText secondaryHeaderEdittext;
    private EditText bodyEdittext;
    private Button saveButton;
    private ImageView imagePreview;
    private CreateArticleViewModel viewModel;
    private Uri uploadedImageUri;

    int SELECT_PICTURE = 200;

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

        view.findViewById(R.id.upload_image_button).setOnClickListener(v -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);

            // pass the constant to compare it
            // with the returned requestCode
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    uploadedImageUri = selectedImageUri;
                    try {
                        // Setting image on image view using Bitmap
                        Bitmap bitmap = MediaStore
                                .Images
                                .Media
                                .getBitmap(
                                        requireActivity().getContentResolver(),
                                        selectedImageUri);
                        imagePreview.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        // Log the exception
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void initViewModel() {
        ViewModelFactory factory = ((MainActivity) requireActivity()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(CreateArticleViewModel.class);
    }

    private void initViews(View view) {
        headerEdittext = view.findViewById(R.id.header_edittext);
        secondaryHeaderEdittext = view.findViewById(R.id.secondary_header_edittext);
        bodyEdittext = view.findViewById(R.id.body_edittext);
        saveButton = view.findViewById(R.id.save_button);
        imagePreview = view.findViewById(R.id.image_preview);
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