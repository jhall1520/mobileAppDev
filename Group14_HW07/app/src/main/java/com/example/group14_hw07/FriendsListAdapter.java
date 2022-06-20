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
public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsViewHolder> {

    ArrayList<User> friendsList;

    public FriendsListAdapter(ArrayList<User> friendsList) {
        this.friendsList = friendsList;
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_layout, parent, false);
        return new FriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {

        User friend = friendsList.get(position);
        holder.userEmail.setText(friend.getEmail());
        holder.userName.setText(friend.getName());
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    static class FriendsViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView userEmail;

        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.textViewFriendName);
            userEmail = itemView.findViewById(R.id.textViewFriendEmail);
        }
    }
}
