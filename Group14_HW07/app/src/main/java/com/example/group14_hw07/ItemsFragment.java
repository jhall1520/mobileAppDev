package com.example.group14_hw07;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemsFragment extends Fragment {

    private static final String ARG_SHOPPINGLIST = "shoppingList";

    private IListener iListener;
    private ShoppingList shoppingList;
    private RecyclerView recyclerView;
    private ItemListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Item> items;
    private TextView totalCost;
    private TextView itemCount;

    public ItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param shoppingList Parameter 1.
     * @return A new instance of fragment ItemsFragment.
     */
    public static ItemsFragment newInstance(ShoppingList shoppingList) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SHOPPINGLIST, shoppingList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.shoppingList = (ShoppingList) getArguments().getSerializable(ARG_SHOPPINGLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        TextView shoppingListName = view.findViewById(R.id.textViewListName);
        itemCount = view.findViewById(R.id.textViewTotalItems);
        totalCost = view.findViewById(R.id.textViewTotalAmount);
        items = shoppingList.getItems();

        shoppingListName.setText(shoppingList.getName());
        if (items != null && !items.isEmpty()) {
            itemCount.setText(items.size() + " items");
            double sum = 0;
            for (int i = 0; i < items.size(); i++) {
                sum += Double.parseDouble(items.get(i).getCost());
            }
            totalCost.setText(String.valueOf(sum));
        } else {
            itemCount.setText("0 items");
            totalCost.setText("$0.00");
        }

        items = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewItems);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ItemListAdapter(items);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        readData();

        ImageView plusSign = view.findViewById(R.id.imageViewAddItem);
        plusSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Enter New Item's Info");
                View inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.add_item_layout, (ViewGroup) getView(), false);
                EditText itemName = inflatedView.findViewById(R.id.editTextItemName);
                EditText cost = inflatedView.findViewById(R.id.editTextCost);
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
                        String name = itemName.getText().toString();
                        String amount = cost.getText().toString();

                        if (name.equals("")) {
                            Toast.makeText(v.getContext(), "Enter Item's name.", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (amount.equals("")) {
                            Toast.makeText(v.getContext(), "Enter Item's cost.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        Map<String, Object> itemMap = new HashMap<>();
                        itemMap.put("name", name);
                        itemMap.put("cost", amount);
                        itemMap.put("status", "need");
                        itemMap.put("userId", null);


                        db.collection("shoppingLists").document(shoppingList.getDocumentId()).collection("items")
                                .add(itemMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

        Button addFriends = view.findViewById(R.id.buttonAddFriends);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (shoppingList.getOwnerId().equals(user.getUid())) {
            addFriends.setVisibility(View.VISIBLE);
            view.findViewById(R.id.buttonAddFriends).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iListener.goToAddFriendsFragment(shoppingList);
                }
            });
        } else
            addFriends.setVisibility(View.INVISIBLE);

        return view;
    }

    public void readData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("shoppingLists").document(shoppingList.getDocumentId())
                .collection("items").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                items.clear();
                for (QueryDocumentSnapshot query : value) {
                    Map<String, Object> map = query.getData();
                    String cost = (String) map.get("cost");
                    String name = (String) map.get("name");
                    String status = (String) map.get("status");
                    String userId = (String) map.get("userId");
                    Item item = new Item(name, cost, status, userId, query.getId(), shoppingList.getDocumentId());
                    items.add(item);
                }

                if(getActivity() == null)
                    return;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        itemCount.setText(items.size() + " items");
                        double sum = 0;
                        for (int i = 0; i < items.size(); i++) {
                            sum += Double.parseDouble(items.get(i).getCost());

                        }
                        totalCost.setText("$" + String.format("%.2f", sum));
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof IListener) {
            iListener = (IListener) context;
        } else
            throw new IllegalStateException("MainActivity does not implement IListener");
    }

    public interface IListener {
        void goToAddFriendsFragment(ShoppingList shoppingList);
    }
}