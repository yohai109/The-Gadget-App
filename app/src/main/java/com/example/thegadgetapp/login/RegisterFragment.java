package com.example.thegadgetapp.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;
import com.example.thegadgetapp.activity.MainActivity;
import com.example.thegadgetapp.database.entities.User;

import java.util.UUID;

public class RegisterFragment extends Fragment {

    RegisterViewModel viewModel;
    String id;
    Button submitBtn;
    EditText username;
    EditText password;
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelFactory factory = ((MainActivity) requireActivity()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(RegisterViewModel.class);

        id = UUID.randomUUID().toString();
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        submitBtn = view.findViewById(R.id.submit_register_button);

        submitBtn.setOnClickListener(v -> {
            User user =new User(
                    id,
                    username.getText().toString(),
                    password.getText().toString());
            viewModel.insert(user);

            });
    }


}
