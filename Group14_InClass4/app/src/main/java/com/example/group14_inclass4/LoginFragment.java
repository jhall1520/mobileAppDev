package com.example.group14_inclass4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * InClass04
 * Group14_InClass04
 * Joel Hall
 * Jimmy Kropp
 */

public class LoginFragment extends Fragment {

    IListener iListener;
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

        view.findViewById(R.id.buttonLogIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataServices.AccountRequestTask task = DataServices.login(email.getText().toString(), password.getText().toString());
                if(task.isSuccessful()){ //successful
                    DataServices.Account account = task.getAccount();
                    iListener.startAccountFragment(account);
                } else { //not successful
                    String error = task.getErrorMessage();
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                }
            }
        });


        view.findViewById(R.id.buttonCreateAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListener.startRegisterFragment();

            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof IListener) {
            iListener = (IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Login");
    }

    public interface IListener {
        void startRegisterFragment();
        void startAccountFragment(DataServices.Account name);
    }


}