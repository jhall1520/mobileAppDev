package com.example.group14_inclass09;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

/**
 * Group14_InClass09
 * NewForumFragment.java
 * Joel Hall
 * Jimmy Kropp
 */
public class NewForumFragment extends Fragment {

    NListener nListener;

    public NewForumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_forum, container, false);

        EditText title = view.findViewById(R.id.editTextForumTitle);
        EditText description = view.findViewById(R.id.editTextForumDescr);

        view.findViewById(R.id.buttonForumSubmit).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                String titleString = title.getText().toString();
                String descriptionString = description.getText().toString();
                if (titleString.equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Error")
                            .setMessage("Enter a title")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                    return;
                } else if (descriptionString.equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Error")
                            .setMessage("Enter a description")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                    return;
                }

                updateData(titleString, descriptionString);
            }
        });

        view.findViewById(R.id.buttonForumCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nListener.goToForumsFromNewForumFragment();
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateData(String title, String description) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

        HashMap<String, Object> forum = new HashMap<>();
        forum.put("title", title);
        forum.put("description", description);
        forum.put("userName", user.getDisplayName());
        forum.put("date", date);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forums")
                .add(forum)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        nListener.goToForumsFromNewForumFragment();
                    }
                });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof NListener) {
            nListener = (NListener) context;
        } else
            throw new IllegalStateException("MainActivity does not implement NListener");
    }

    public interface NListener {
        void goToForumsFromNewForumFragment();
    }
}