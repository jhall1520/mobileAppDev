package com.example.group14_hw07;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    ArrayList<Item> items;

    public ItemListAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item curItem = items.get(position);
        holder.itemName.setText(curItem.getName());
        holder.itemCost.setText("$" + String.format("%.2f", Double.parseDouble(curItem.getCost())));
        String status = curItem.getStatus();

        if (status.equals("need")) {
            holder.checkBox.setImageResource(R.drawable.unchecked_box);
        } else {
            holder.checkBox.setImageResource(R.drawable.checked_box);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId;
                String changedStatus;
                if (status.equals("need")) {
                    changedStatus = "acquired";
                    holder.checkBox.setImageResource(R.drawable.checked_box);
                    userId = user.getUid();
                } else {
                    changedStatus = "need";
                    holder.checkBox.setImageResource(R.drawable.unchecked_box);
                    userId = null;
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("shoppingLists").document(curItem.getShoppingListDocumentId())
                        .collection("items").document(curItem.getDocumentId()).update("status", changedStatus)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                db.collection("shoppingLists").document(curItem.getShoppingListDocumentId())
                                        .collection("items").document(curItem.getDocumentId()).update("userId", userId);
                            }
                        });

            }
        });

        holder.trash.setVisibility(View.VISIBLE);
        holder.trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("shoppingLists").document(curItem.getShoppingListDocumentId())
                            .collection("items").document(curItem.getDocumentId())
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemCost;
        ImageView checkBox;
        ImageView trash;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.textViewItemName);
            itemCost = itemView.findViewById(R.id.textViewItemCost);
            checkBox = itemView.findViewById(R.id.imageViewCheckBox);
            trash = itemView.findViewById(R.id.imageViewRemoveItem);


        }
    }
}
