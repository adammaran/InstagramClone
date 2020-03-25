package com.example.instagramclone.Adapters.ViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Common.Data;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.Models.UserModel;
import com.example.instagramclone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//todo fix so that it fulls only posts from user which profile is showing
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private ArrayList<FeedItemModel> postList;

    //Make api for only pulling post for specific user!!!
    public PostsAdapter(ArrayList<FeedItemModel> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_profile_post, parent, false);

        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        Picasso.get().load(postList.get(position).getImageURL()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.user_profile_post_image);
        }
    }
}

