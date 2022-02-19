package com.example.thegadgetapp.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView secondPassword;

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

        initViews(view);

        submitBtn.setOnClickListener(v -> {
            User user = new User(
                    id,
                    username.getText().toString(),
                    password.getText().toString()
            );
            if (user.username.equals("") || user.password.equals("")) {
                Toast.makeText(getContext(), "please enter username and password", Toast.LENGTH_LONG).show();
            } else if (!user.password.equals(secondPassword.getText().toString())) {
                Toast.makeText(getContext(), "password do not match", Toast.LENGTH_LONG).show();
            } else {
                viewModel.insertUserRemote(user, isSuccessful -> {
                    if (isSuccessful) {
                        viewModel.insertUserLocal(user, isSuccessful2 -> {
                            Toast.makeText(getContext(), "registration successful", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigateUp();
                        });
                    } else {
                        mainThread.post(() -> {
                            Toast.makeText(getContext(), "username already taken", Toast.LENGTH_LONG).show();
                        });
                    }
                });
            }
        });
    }

    private void initViews(@NonNull View view) {
        id = UUID.randomUUID().toString();
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        submitBtn = view.findViewById(R.id.submit_register_button);
        secondPassword = view.findViewById(R.id.password2);
    }


}
