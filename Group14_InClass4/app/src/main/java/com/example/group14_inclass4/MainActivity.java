package com.example.group14_inclass4;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * InClass04
 * Group14_InClass04
 * Joel Hall
 * Jimmy Kropp
 */

public class MainActivity extends AppCompatActivity implements LoginFragment.IListener, RegisterFragment.RListener, AccountFragment.AListener, UpdateFragment.UListener {

    DataServices.Account account = null;

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Main Activity");

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new LoginFragment(), "LogIn")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void startRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new RegisterFragment(), "Register")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void startAccountFragment(DataServices.Account account) {
        this.account = account;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, AccountFragment.newInstance(account), "Account")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void startLoginFragmentFromRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LoginFragment(), "LogIn")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void startLoginFragmentFromAccount() {
        account = null;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LoginFragment(), "Login")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void startUpdateFragment(DataServices.Account account) {
        this.account = account;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, UpdateFragment.newInstance(account), "Update")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goBackToAccountFragmentFromUpdate() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void updateAccountSendToAccountFragment(DataServices.Account account) {
        this.account = account;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, AccountFragment.newInstance(account), "Account")
                .addToBackStack("Account")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void startAccountFragmentFromRegister(DataServices.Account account) {
        this.account = account;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, AccountFragment.newInstance(account), "Login")
                .addToBackStack(null)
                .commit();
    }

}