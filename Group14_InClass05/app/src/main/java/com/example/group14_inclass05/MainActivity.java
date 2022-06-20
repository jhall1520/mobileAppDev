package com.example.group14_inclass05;

/**
 * Group14_InClass05
 * MainActivity.java
 * Joel Hall
 * Jimmy Kropp
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AppCategoriesFragment.CListener, AppNamesFragment.NListener {
    ArrayList<String> categories = DataServices.getAppCategories();

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Main Activity");

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new AppCategoriesFragment(),"appCategories")
                .commit();
    }

    @Override
    public void startAppNamesFragment(String category) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, AppNamesFragment.newInstance(category), "appNames")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void startAppDetailsFragment(DataServices.App app) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, AppDetailsFragment.newInstance(app), "appDetails")
                .addToBackStack(null)
                .commit();
    }
}