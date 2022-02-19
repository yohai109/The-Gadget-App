package com.example.thegadgetapp.editarticle;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;
import com.example.thegadgetapp.activity.MainActivity;
import com.example.thegadgetapp.database.entities.Article;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class EditArticleFragment extends Fragment {
    private EditText titleEditTextView;
    private EditText secondaryHeaderEditTextView;
    private EditText bodyEditTextView;
    private Button saveButton;
    private ImageView imagePreview;
    private ProgressBar loadingView;
    private FloatingActionButton fab;

    private String articleId;

    private EditArticleViewModel viewModel;
    private Uri uploadedImageUri;

    int SELECT_PICTURE = 200;
    private Article currArticle;

    public EditArticleFragment() {
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

        String index = EditArticleFragmentArgs.fromBundle(getArguments()).getArticleId();

        viewModel.getArticle(articleId).observe(getViewLifecycleOwner(), article -> {
            if (article != null ) {
                currArticle = article;
                titleEditTextView.setText(article.header);
                secondaryHeaderEditTextView.setText(article.secondaryHeader);
                bodyEditTextView.setText(article.body);
                Picasso.get()
                        .load(article.imageUri)
                        .into(imagePreview);

            }
        });
        view.findViewById(R.id.upload_image_button).setOnClickListener(v -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);

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
        viewModel = new ViewModelProvider(this, factory).get(EditArticleViewModel.class);
    }

    private void initViews(View view) {
        titleEditTextView = view.findViewById(R.id.title_text);
        secondaryHeaderEditTextView = view.findViewById(R.id.secondary_header_text);
        bodyEditTextView = view.findViewById(R.id.body_tex);
        saveButton = view.findViewById(R.id.save_button);
        imagePreview = view.findViewById(R.id.image_imageview);
        fab = view.findViewById(R.id.edit_article_fab);
    }

    private void setSaveClick() {
        saveButton.setOnClickListener(v -> {
            loadingView.setVisibility(View.VISIBLE);
                uploadImage();
        });
    }

    private void uploadImage() {
        // Get the data from an ImageView as bytes
        imagePreview.setDrawingCacheEnabled(true);
        imagePreview.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imagePreview.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        viewModel.uploadImage(data, uri -> {
            uploadedImageUri = uri;
            saveArticle();
        });
    }

    private void saveArticle() {
        String header = titleEditTextView.getText().toString();
        String secondaryHeader = secondaryHeaderEditTextView.getText().toString();
        String body = bodyEditTextView.getText().toString();
        Article newArticle = new Article(
                articleId,
                ((MainActivity) requireActivity()).currUserId,
                header,
                secondaryHeader,
                body,
                uploadedImageUri.toString()
        );

        viewModel.saveArticle(newArticle);
        loadingView.setVisibility(View.GONE);
        Navigation.findNavController(saveButton).navigateUp();
    }
}