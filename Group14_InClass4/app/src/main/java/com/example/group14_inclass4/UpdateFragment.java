package com.example.group14_inclass4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * InClass04
 * Group14_InClass04
 * Joel Hall
 * Jimmy Kropp

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACCOUNT = "account";

    private DataServices.Account account;
    private UListener uListener;


    public UpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param account Parameter 1.
     * @return A new instance of fragment UpdateFragment.
     */
    public static UpdateFragment newInstance(DataServices.Account account) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACCOUNT, account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = (DataServices.Account) getArguments().getSerializable(ARG_ACCOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        TextView email = view.findViewById(R.id.textViewShowEmail);
        email.setText(account.getEmail());

        EditText name = view.findViewById(R.id.editTextName);
        EditText password = view.findViewById(R.id.editTextPassword);


        view.findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataServices.AccountRequestTask task = DataServices.update(account, name.getText().toString(),
                       password.getText().toString());

                if(task.isSuccessful()){ //successful
                    DataServices.Account account = task.getAccount();
                    uListener.updateAccountSendToAccountFragment(account);
                } else { //not successful
                    String error = task.getErrorMessage();
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.buttonCancelUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uListener.goBackToAccountFragmentFromUpdate();
            }
        });


        return  view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof UListener) {
            uListener = (UListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Update Account");
    }

    public interface UListener {
        void goBackToAccountFragmentFromUpdate();
        void updateAccountSendToAccountFragment(DataServices.Account account);
    }
}