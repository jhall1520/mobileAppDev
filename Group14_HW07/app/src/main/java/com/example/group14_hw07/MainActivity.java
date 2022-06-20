package com.example.group14_hw07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.LListener, SignUpFragment.CListener, ShoppingListFragment.FListener,
        ShoppingListAdapter.SListener, ItemsFragment.IListener {

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
                .replace(R.id.container, new SignUpFragment(), "signUp")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToShoppingListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ShoppingListFragment(), "shopping")
                .commit();
    }

    @Override
    public void goToShoppingListFragmentFromCreate() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ShoppingListFragment(), "shopping")
                .commit();
    }

    @Override
    public void goToLoginFragmentFromCreate() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goToLoginFragmentFromShoppingList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LoginFragment(), "login")
                .commit();
    }

    @Override
    public void goToItemListFragment(ShoppingList shoppingList) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ItemsFragment.newInstance(shoppingList), "items")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToAddFriendsFragment(ShoppingList shoppingList) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, AddFriendsFragment.newInstance(shoppingList), "friends")
                .addToBackStack(null)
                .commit();
    }
}