package com.example.thegadgetapp.profile;

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
import androidx.navigation.Navigation;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;
import com.example.thegadgetapp.activity.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    TextView usernameTextView;
    TextView nameTextView;
    ImageView avatarImageView;
    ProfileViewModel viewModel;
    FloatingActionButton fab;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameTextView = view.findViewById(R.id.username_text_view);
        nameTextView = view.findViewById(R.id.name);
        avatarImageView = view.findViewById(R.id.avatar);
        fab = view.findViewById(R.id.profile_fab);

        ViewModelFactory factory = ((MainActivity) requireActivity()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);
        viewModel.getCurrUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                usernameTextView.setText(user.username);
                nameTextView.setText(user.name);
                if (user.avatarUri != null && !user.avatarUri.equals("")) {
                    Picasso.get()
                            .load(user.avatarUri)
                            .into(avatarImageView);
                }
            }
        });

        fab.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(
                    ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            );
        });
    }
}