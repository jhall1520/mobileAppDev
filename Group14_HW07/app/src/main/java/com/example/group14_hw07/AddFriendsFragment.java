package com.example.group14_hw07;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class AddFriendsFragment extends Fragment {

    private static final String ARG_SHOPPINGLIST = "shoppingList";
    private ShoppingList curShoppingList;
    ArrayList<User> users;
    RecyclerView recyclerView;
    AddUserAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public AddFriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param shoppingList Parameter 1.
     * @return A new instance of fragment ItemsFragment.
     */
    public static AddFriendsFragment newInstance(ShoppingList shoppingList) {
        AddFriendsFragment fragment = new AddFriendsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SHOPPINGLIST, shoppingList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.curShoppingList = (ShoppingList) getArguments().getSerializable(ARG_SHOPPINGLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_friends, container, false);

        users = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewAddUsers);
        adapter = new AddUserAdapter(users);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        readData();


        return view;
    }

    public void readData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("shoppingLists").document(curShoppingList.getDocumentId())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();

                Map<String, Object> data = documentSnapshot.getData();
                ArrayList<String> userIds = (ArrayList<String>) data.get("userIds");

                FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();

                db.collection("users")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Map<String, Object> data = document.getData();
                                        String name = (String) data.get("name");
                                        String email = (String) data.get("email");
                                        String id = (String) data.get("id");

                                        if (!curUser.getUid().equals(id)) {
                                            User user = new User(name, email, id, curShoppingList.getDocumentId(), userIds.contains(id));
                                            users.add(user);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });

    }
}