package com.example.thegadgetapp.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.thegadgetapp.MainApplication;
import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;

public class LoginFragment extends Fragment {
    LoginViewModel viewModel;
    Button loginBtn;
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModelFactory factory = ((MainApplication) requireActivity().getApplication()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);

        loginBtn = view.findViewById(R.id.login_button);
        loginBtn.setOnClickListener(v -> {
            viewModel.tryLogin("yohai", "123", (isSuccess, user) -> {
                Log.d("login", "tried to login " + isSuccess);
                if (isSuccess) {
                    mainThread.post(() -> {
                        Navigation.findNavController(v).navigate(
                                LoginFragmentDirections.actionLoginFragmentToNewsFeedFragment(user.id)
                        );
                    });
                }
            });
        });
    }
}