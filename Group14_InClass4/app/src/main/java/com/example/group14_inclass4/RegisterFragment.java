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

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        // Required empty public constructor
    }

    RListener rListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        EditText name = view.findViewById(R.id.editTextName);
        EditText email = view.findViewById(R.id.editTextRegisterEmail);
        EditText password = view.findViewById(R.id.editTextRegisterPassword);

        view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataServices.AccountRequestTask task = DataServices.register(name.getText().toString(),
                        email.getText().toString(), password.getText().toString());

                if(task.isSuccessful()){ //successful
                    DataServices.Account account = task.getAccount();
                    rListener.startAccountFragmentFromRegister(account);
                } else { //not successful
                    String error = task.getErrorMessage();
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                }

            }
        });

        view.findViewById(R.id.buttonCancelRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rListener.startLoginFragmentFromRegister();
            }
        });


        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof RListener) {
            rListener = (RListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Register Account");
    }

    public interface RListener {
        void startAccountFragmentFromRegister(DataServices.Account account);
        void startLoginFragmentFromRegister();
    }

}