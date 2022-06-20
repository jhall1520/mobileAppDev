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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddUserAdapter extends RecyclerView.Adapter<AddUserAdapter.UserViewHolder> {
    ArrayList<User> users;

    public AddUserAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User curUser = users.get(position);
        holder.userName.setText(curUser.getName());
        holder.userEmail.setText(curUser.getEmail());

        if (!curUser.isAdded()) {
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
                if (!curUser.isAdded()) {
                    curUser.setAdded(true);
                    holder.checkBox.setImageResource(R.drawable.checked_box);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("shoppingLists").document(curUser.getShoppingListDocument())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            Map<String, Object> data = documentSnapshot.getData();
                            ArrayList<String> userIds = (ArrayList<String>) data.get("userIds");
                            userIds.add(curUser.getId());

                            Map<String, Object> updatedData = new HashMap<>();
                            updatedData.put("userIds", userIds);

                            db.collection("shoppingLists").document(curUser.getShoppingListDocument())
                                    .update("userIds", userIds)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                        }
                                    });
                        }
                    });


                } else {
                    curUser.setAdded(false);
                    holder.checkBox.setImageResource(R.drawable.unchecked_box);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("shoppingLists").document(curUser.getShoppingListDocument())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            Map<String, Object> data = documentSnapshot.getData();
                            ArrayList<String> userIds = (ArrayList<String>) data.get("userIds");
                            userIds.remove(curUser.getId());

                            Map<String, Object> updatedData = new HashMap<>();
                            updatedData.put("userIds", userIds);

                            db.collection("shoppingLists").document(curUser.getShoppingListDocument())
                                    .update("userIds", userIds)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                        }
                                    });
                        }
                    });
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView userEmail;
        ImageView checkBox;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.textViewUserName);
            userEmail = itemView.findViewById(R.id.textViewUserEmail);
            checkBox = itemView.findViewById(R.id.imageViewCB);


        }
    }

}
