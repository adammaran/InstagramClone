package com.example.instagramclone.Adapters.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Activities.MainActivity;
import com.example.instagramclone.Common.Data;
import com.example.instagramclone.Fragments.OnePostFragment;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.Models.UserModel;
import com.example.instagramclone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private ArrayList<FeedItemModel> postList;
    private Context context;

    public PostsAdapter(ArrayList<FeedItemModel> postList) {
        this.postList = postList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_profile_post, parent, false);

        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(postList.get(position).getImageBuffer().getData(), 0, postList.get(position).getImageBuffer().getData().length);
        holder.image.setImageBitmap(bitmap);
        holder.image.setOnClickListener(view -> {
            MainActivity activity = (MainActivity) view.getContext();
            activity.loadFragment(new OnePostFragment(postList.get(position)));
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.user_profile_post_image);

        }
    }
}

