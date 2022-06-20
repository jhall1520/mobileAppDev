package com.example.group14_inclass09;

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
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Group14_InClass09
 * CreateAccountFragment.java
 * Joel Hall
 * Jimmy Kropp
 */
public class CreateAccountFragment extends Fragment {

    CListener cListener;

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        EditText name = view.findViewById(R.id.editTextUserName);
        EditText email = view.findViewById(R.id.editTextUserEmail);
        EditText password = view.findViewById(R.id.editTextUserPassword);



        view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = name.getText().toString();
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();

                if (userName.equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Error")
                            .setMessage("Enter your name")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                    return;
                } else if (emailString.equals("")) {
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

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName)
                                    .build();
                            user.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    cListener.goToFormsFragmentFromCreate();
                                }
                            });

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

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cListener.goToLoginFragmentFromCreate();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CListener) {
            cListener = (CListener) context;
        } else
            throw new IllegalStateException("MainActivity does not implement CListener");
    }

    public interface CListener {
        void goToFormsFragmentFromCreate();
        void goToLoginFragmentFromCreate();
    }
}