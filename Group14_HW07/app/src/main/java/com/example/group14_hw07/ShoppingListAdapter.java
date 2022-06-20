package com.example.group14_hw07;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Group14_InClass09
 * ForumAdapter.java
 * Joel Hall
 * Jimmy Kropp
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingViewHolder> {

    ArrayList<ShoppingList> shoppingList;
    SListener sListener;

    public ShoppingListAdapter(ArrayList<ShoppingList> shoppingList, SListener sListener) {
        this.shoppingList = shoppingList;
        this.sListener = sListener;
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_layout, parent, false);
        return new ShoppingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
        ShoppingList curShoppingList = shoppingList.get(position);
        holder.shoppingListName.setText(curShoppingList.getName());
        ArrayList<Item> items = curShoppingList.getItems();
        if (items != null && !items.isEmpty()) {
            holder.totalItems.setText(String.valueOf(items.size()) + " items");
            double sum = 0;
            for (int i = 0; i < items.size(); i++) {
                sum += Double.parseDouble(items.get(i).getCost());
            }
            holder.totalCost.setText("$" + String.format("%.2f", sum));
        } else {
            holder.totalItems.setText("0 items");
            holder.totalCost.setText("$0.00");
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user.getUid().equals(curShoppingList.getOwnerId())) {
            holder.trash.setVisibility(View.VISIBLE);
            holder.trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("shoppingLists").document(curShoppingList.getDocumentId())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
            });
        } else {
            holder.trash.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sListener.goToItemListFragment(curShoppingList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    public interface SListener {
        void goToItemListFragment(ShoppingList shoppingList);
    }

    static class ShoppingViewHolder extends RecyclerView.ViewHolder {

        TextView shoppingListName;
        TextView totalItems;
        TextView totalCost;
        ImageView trash;

        public ShoppingViewHolder(@NonNull View itemView) {
            super(itemView);

            shoppingListName = itemView.findViewById(R.id.textViewShoppingListName);
            totalCost = itemView.findViewById(R.id.textViewTotalCost);
            totalItems = itemView.findViewById(R.id.textViewAmountItems);
            trash = itemView.findViewById(R.id.imageViewTrash);


        }
    }
}