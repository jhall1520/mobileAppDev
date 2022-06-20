package com.example.group14_hw07;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Group14_InClass09
 * ForumsFragment.java
 * Joel Hall
 * Jimmy Kropp
 */
public class ShoppingListFragment extends Fragment {

    FListener fListener;
    ArrayList<ShoppingList> shoppingLists;
    ArrayList<User> friendList;
    ArrayList<String> shoppingListId;
    ArrayList<String> friendIds;
    RecyclerView recyclerView;
    ShoppingListAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    RecyclerView friendsRecyclerView;
    RecyclerView.LayoutManager friendsLayoutManager;
    FriendsListAdapter friendsListAdapter;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        shoppingLists = new ArrayList<>();
        friendList = new ArrayList<>();
        shoppingListId = new ArrayList<>();
        friendIds = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewShopping);
        adapter = new ShoppingListAdapter(shoppingLists, (ShoppingListAdapter.SListener) this.getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        friendsRecyclerView = view.findViewById(R.id.recyclerViewFreinds);
        friendsListAdapter = new FriendsListAdapter(friendList);
        friendsLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        friendsRecyclerView.setLayoutManager(friendsLayoutManager);
        friendsRecyclerView.setAdapter(friendsListAdapter);

        readData();

        view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                fListener.goToLoginFragmentFromShoppingList();
            }
        });

        view.findViewById(R.id.imageViewAddSL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Enter Shopping List's Name");
                View inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.shopping_list_info, (ViewGroup) getView(), false);
                EditText shoppingListName = inflatedView.findViewById(R.id.editTextSLName);
                alertDialog.setView(inflatedView);

                alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String name = shoppingListName.getText().toString();

                        if (name.equals("")) {
                            Toast.makeText(v.getContext(), "Enter your name.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        Map<String, Object> shoppingListMap = new HashMap<>();
                        shoppingListMap.put("name", name);
                        shoppingListMap.put("ownerId", user.getUid());
                        ArrayList<String> userIds = new ArrayList<>();
                        userIds.add(user.getUid());
                        shoppingListMap.put("userIds", userIds);


                        db.collection("shoppingLists")
                                .add(shoppingListMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }
        });


        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void readData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("shoppingLists")
                .whereArrayContainsAny("userIds", Arrays.asList(user.getUid()))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        shoppingLists.clear();
                        friendList.clear();
                        friendIds.clear();
                        shoppingListId.clear();
                        for (QueryDocumentSnapshot query : value) {
                            Map<String, Object> forumMap = query.getData();
                            String listName = (String) forumMap.get("name");
                            String ownerId = (String) forumMap.get("ownerId");
                            ArrayList<String> userIds = (ArrayList<String>) forumMap.get("userIds");
                            String shoppingListDocumentId = query.getId();

                            for (String id : userIds) {
                                if (!user.getUid().equals(id)) {
                                        db.collection("users").whereEqualTo("id", id).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                for (QueryDocumentSnapshot query : value) {
                                                    Map<String, Object> map = query.getData();
                                                    String userName = (String) map.get("name");
                                                    String userId = (String) map.get("id");
                                                    String userEmail = (String) map.get("email");

                                                    User user1 = new User(userName, userEmail, userId, null, false);
                                                    if (!friendIds.contains(userId)) {
                                                        friendList.add(user1);
                                                        friendIds.add(userId);
                                                    }
                                                }
                                                friendsListAdapter.notifyDataSetChanged();
                                            }
                                        });
                                }
                            }

                            ArrayList<Item> items = new ArrayList<>();

                            db.collection("shoppingLists").document(query.getId())
                                    .collection("items").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    for (QueryDocumentSnapshot query : value) {
                                        Map<String, Object> map = query.getData();
                                        String cost = (String) map.get("cost");
                                        String name = (String) map.get("name");
                                        String status = (String) map.get("status");
                                        String userId = (String) map.get("userId");
                                        Item item = new Item(name, cost, status, userId, query.getId(), shoppingListDocumentId);
                                        items.add(item);
                                    }
                                    ShoppingList shoppingList = new ShoppingList(listName, items, ownerId, userIds, shoppingListDocumentId);
                                    if (!shoppingListId.contains(shoppingListDocumentId)) {
                                        shoppingLists.add(shoppingList);
                                        shoppingListId.add(shoppingListDocumentId);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
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
        void goToLoginFragmentFromShoppingList();
    }
}