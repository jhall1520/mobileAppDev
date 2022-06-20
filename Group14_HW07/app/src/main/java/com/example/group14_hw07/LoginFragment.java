package com.example.group14_hw07;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Group14_InClass09
 * LoginFragment.java
 * Joel Hall
 * Jimmy Kropp
 */
public class LoginFragment extends Fragment {

    LListener lListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText email = view.findViewById(R.id.editTextEmailAddress);
        EditText password = view.findViewById(R.id.editTextPassword);

        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();

                if (emailString.equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Error")
                            .setMessage("Enter an email")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                    return;
                } else if (passwordString.equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Error")
                            .setMessage("Enter a password")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                    return;
                }

                FirebaseAuth mAuth  = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            lListener.goToShoppingListFragment();
                        } else {
                            // If sign in fails, display a message to the user.
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                    .setTitle("Error")
                                    .setMessage(task.getException().getMessage())
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show();
                        }
                    }
                });
            }
        });

        view.findViewById(R.id.buttonCreateAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lListener.goToCreateAccountFragment();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof LListener) {
            lListener = (LListener) context;
        } else
            throw new IllegalStateException("MainActivity does not implement LListener");
    }

    public interface LListener {
        void goToCreateAccountFragment();
        void goToShoppingListFragment();
    }
}