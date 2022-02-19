package com.example.thegadgetapp.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;
import com.example.thegadgetapp.activity.MainActivity;

public class ProfileFragment extends Fragment {

    TextView usernameTextView;
    ProfileViewModel viewModel;

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

        ViewModelFactory factory = ((MainActivity) requireActivity()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);
        viewModel.getCurrUser().observe(getViewLifecycleOwner(), user -> {
            usernameTextView.setText(user.username);
        });
    }
}