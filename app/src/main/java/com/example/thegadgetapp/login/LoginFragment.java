package com.example.thegadgetapp.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginFragment extends Fragment {
    private LoginViewModel viewModel;
    private Button loginBtn;
    private Button registerBtn;
    private Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());
    private EditText usernameEditText;
    private EditText passwordEditText;

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
        ViewModelFactory factory = ((MainActivity) requireActivity()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);

        hideLogoutButton();

        String currUserId = viewModel.getCurrUserId();
        if (currUserId != null) {
            Navigation.findNavController(view).navigate(
                    LoginFragmentDirections.actionLoginFragmentToNewsFeedFragment(currUserId)
            );
        } else {
            initViews(view);

            loginBtn.setOnClickListener(v -> {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(getContext(), "please enter username and password", Toast.LENGTH_LONG).show();
                } else {
                    viewModel.tryLogin(username, password, (isSuccess, user) -> {
                        mainThread.post(() -> {
                            if (isSuccess) {
                                ((MainActivity) requireActivity()).currUserId = user.id;
                                viewModel.setCurrLoginUser(user.id);
                                Navigation.findNavController(v).navigate(
                                        LoginFragmentDirections.actionLoginFragmentToNewsFeedFragment(user.id)
                                );
                            } else {
                                Toast.makeText(getContext(), "wrong username or password", Toast.LENGTH_LONG).show();
                            }
                        });
                    });
                }
            });

            registerBtn.setOnClickListener(v -> {
                Navigation.findNavController(v).navigate(
                        LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                );
            });

        }
    }

    private void initViews(@NonNull View view) {
        loginBtn = view.findViewById(R.id.login_button);
        registerBtn = view.findViewById(R.id.register_button);
        usernameEditText = view.findViewById(R.id.username);
        passwordEditText = view.findViewById(R.id.password);
    }

    private void hideLogoutButton() {
        ((MainActivity) requireActivity()).toggleLogoutButton(false);
    }
}