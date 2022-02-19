package com.example.thegadgetapp.editprofile;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;
import com.example.thegadgetapp.activity.MainActivity;
import com.example.thegadgetapp.database.entities.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditProfileFragment extends Fragment {

    private static final int SELECT_PICTURE = 200;
    private TextView username;
    private EditText name;
    private ImageView imageView;
    private FloatingActionButton fab;
    private Button uploadButton;
    private Uri uploadedImageUri;
    private ProgressBar loadingView;
    private User userCech;


    private EditProfileViewModel viewModel;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelFactory factory = ((MainActivity) requireActivity()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(EditProfileViewModel.class);

        initViews(view);
        fillFields();
        setClickListeners();
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
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        // Log the exception
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void fillFields() {
        viewModel.getCurrUser().observe(getViewLifecycleOwner(), user -> {
            userCech = user;
            username.setText(user.username);
            name.setText(user.name);
            if (user.avatarUri != null && !user.avatarUri.equals("")) {
                Picasso.get().load(user.avatarUri).into(imageView);
            }
        });
    }

    private void initViews(@NonNull View view) {
        username = view.findViewById(R.id.username_text_view);
        name = view.findViewById(R.id.name);
        imageView = view.findViewById(R.id.avatar);
        fab = view.findViewById(R.id.edit_profile_fab);
        uploadButton = view.findViewById(R.id.upload_image_button);
        loadingView = view.findViewById(R.id.loading_spinner);
    }

    private void setClickListeners() {
        fab.setOnClickListener(v -> {
            loadingView.setVisibility(View.VISIBLE);
            uploadImage();
        });
        uploadButton.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);

            // pass the constant to compare it
            // with the returned requestCode
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        });
    }

    private void uploadImage() {
        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        viewModel.uploadImage(data, uri -> {
            uploadedImageUri = uri;
            saveUser();
        });
    }

    private void saveUser(){
        User user = new User(
                userCech.id,
                userCech.username,
                userCech.password,
                uploadedImageUri.toString(),
                name.getText().toString()
        );
        viewModel.updateUser(user);
        loadingView.setVisibility(View.GONE);
        Navigation.findNavController(username).navigateUp();
    }
}