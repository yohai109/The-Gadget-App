package com.example.thegadgetapp.activity;

import android.os.Bundle;
import android.view.Menu;

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
    Toolbar toolbar;

    private Boolean isLogoutBtnShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewModelFactory factory = ((MainApplication) getApplication()).getFactory();
        viewModel = new ViewModelProvider(this, factory).get(ActivityViewModel.class);
        viewModel.initDB();

        initToolbar();
        initNavigation();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        isLogoutBtnShown = false;


    }

    private void initNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.loginFragment,
                R.id.newsFeedFragment
        ).build();

        NavigationUI.setupWithNavController(
                toolbar,
                navController,
                appBarConfiguration
        );

        setMenuItemsClickListener(navController);
    }

    private void setMenuItemsClickListener(NavController navController) {
        Menu menu = toolbar.getMenu();
        menu.findItem(R.id.logout).setOnMenuItemClickListener(item -> {
            viewModel.logout();
            navController.navigate(R.id.action_global_loginFragment);
            return true;
        });

        menu.findItem(R.id.profile).setOnMenuItemClickListener(item -> {
            navController.navigate(R.id.action_global_ProfileFragment);
            return true;
        });
    }

    public ViewModelFactory getFactory() {
        return ((MainApplication) getApplication()).getFactory();
    }

    public void toggleLogoutButton(Boolean shouldShow) {
        if (shouldShow != isLogoutBtnShown) {
            isLogoutBtnShown = shouldShow;
            Menu menu = toolbar.getMenu();
            menu.findItem(R.id.logout).setVisible(isLogoutBtnShown);
            menu.findItem(R.id.profile).setVisible(isLogoutBtnShown);
            toolbar.invalidateMenu();
        }
    }
}