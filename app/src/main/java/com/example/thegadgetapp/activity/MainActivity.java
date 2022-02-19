package com.example.thegadgetapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.thegadgetapp.MainApplication;
import com.example.thegadgetapp.R;
import com.example.thegadgetapp.ViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityViewModel viewModel;
    public String currUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewModelFactory factory = ((MainApplication) getApplication()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(ActivityViewModel.class);
        viewModel.initDB();

        Toolbar toolbar = findViewById(R.id.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        NavigationUI.setupWithNavController(
                toolbar,
                navController,
                appBarConfiguration
        );
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public ViewModelFactory getFactory() {
        return ((MainApplication) getApplication()).getFactory();
    }
}