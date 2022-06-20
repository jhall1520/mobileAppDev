package com.example.group14_inclass09;

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
public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {

    ArrayList<Forum> forums;

    public ForumAdapter(ArrayList<Forum> forums) {
        this.forums = forums;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_layout, parent, false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        Forum forum = forums.get(position);
        holder.forumTitle.setText(forum.getTitle());
        holder.forumDescription.setText(forum.getDescription());
        holder.userName.setText(forum.getUserName());
        holder.date.setText(forum.getDate());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user.getDisplayName().equals(forum.getUserName())) {
            holder.trash.setVisibility(View.VISIBLE);
            holder.trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("forums").document(forum.getDocumentId())
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
    }

    @Override
    public int getItemCount() {
        return forums.size();
    }

    static class ForumViewHolder extends RecyclerView.ViewHolder {

        TextView forumTitle;
        TextView forumDescription;
        TextView userName;
        TextView date;
        ImageView trash;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);

            forumTitle = itemView.findViewById(R.id.textViewForumTitle);
            forumDescription = itemView.findViewById(R.id.textViewForumDescr);
            userName = itemView.findViewById(R.id.textViewUser);
            date = itemView.findViewById(R.id.textViewDate);
            trash = itemView.findViewById(R.id.imageViewTrash);


        }
    }
}
