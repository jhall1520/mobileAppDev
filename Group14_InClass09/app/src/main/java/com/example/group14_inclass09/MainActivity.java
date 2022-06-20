package com.example.group14_inclass09;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Group14_InClass09
 * MainActivity.java
 * Joel Hall
 * Jimmy Kropp
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.LListener, NewForumFragment.NListener,
        ForumsFragment.FListener, CreateAccountFragment.CListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new LoginFragment(), "login")
                .commit();
    }

    @Override
    public void goToCreateAccountFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new CreateAccountFragment(), "create")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToForumsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ForumsFragment(), "forums")
                .commit();
    }

    @Override
    public void goToForumsFromNewForumFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goToLoginFragmentFromForums() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LoginFragment(), "login")
                .commit();
    }

    @Override
    public void goToNewForumFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new NewForumFragment(), "newForum")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToFormsFragmentFromCreate() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ForumsFragment(), "forums")
                .commit();
    }

    @Override
    public void goToLoginFragmentFromCreate() {
        getSupportFragmentManager().popBackStack();
    }
}