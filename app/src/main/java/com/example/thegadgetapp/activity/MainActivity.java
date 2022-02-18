package com.example.thegadgetapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.thegadgetapp.MainApplication;
import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;

public class MainActivity extends AppCompatActivity {
    ActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewModelFactory factory = ((MainApplication) getApplication()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(ActivityViewModel.class);
        viewModel.initDB();
    }
}