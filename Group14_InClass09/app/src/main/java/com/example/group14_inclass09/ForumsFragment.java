package com.example.group14_inclass09;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Group14_InClass09
 * ForumsFragment.java
 * Joel Hall
 * Jimmy Kropp
 */
public class ForumsFragment extends Fragment {

    FListener fListener;
    ArrayList<Forum> forums;
    RecyclerView recyclerView;
    ForumAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public ForumsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forums, container, false);

        forums = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new ForumAdapter(forums);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        readData();

        view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                fListener.goToLoginFragmentFromForums();
            }
        });

        view.findViewById(R.id.buttonNewForum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fListener.goToNewForumFragment();
            }
        });


        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void readData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("forums")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        forums.clear();
                        for (QueryDocumentSnapshot query : value) {
                            Map<String, Object> forumMap = query.getData();
                            String title = (String) forumMap.get("title");
                            String description = (String) forumMap.get("description");
                            String userName = (String) forumMap.get("userName");
                            String date = (String) forumMap.get("date");
                            Forum forum = new Forum(title, userName,
                                    description, date, query.getId());

                            forums.add(forum);
                        }

                        adapter.notifyDataSetChanged();

                    }
                });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof FListener) {
            fListener = (FListener) context;
        } else
            throw new IllegalStateException("MainActivity does not implement FListener");
    }

    public interface FListener {
        void goToLoginFragmentFromForums();

        void goToNewForumFragment();
    }
}