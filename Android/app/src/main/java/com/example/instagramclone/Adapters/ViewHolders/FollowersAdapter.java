package com.example.instagramclone.Adapters.ViewHolders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Activities.MainActivity;
import com.example.instagramclone.Fragments.UserProfileFragment;
import com.example.instagramclone.Models.FollowerModel;
import com.example.instagramclone.R;

import java.util.ArrayList;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.FollowersPlaceHolder> {

    private ArrayList<FollowerModel> list;

    public FollowersAdapter(ArrayList<FollowerModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FollowersPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followers, parent, false);
        return new FollowersPlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowersPlaceHolder holder, int position) {
        holder.username.setText(list.get(position).getUsername());
        Bitmap bitmap = BitmapFactory.decodeByteArray(list.get(position).getAvatar().getData(), 0, list.get(position).getAvatar().getData().length);
        holder.avatar.setImageBitmap(bitmap);
        holder.layout.setOnClickListener(view -> {
            MainActivity main = (MainActivity) view.getContext();
            main.loadFragment(new UserProfileFragment(list.get(position).getID(), false));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FollowersPlaceHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout;
        private ImageView avatar;
        private TextView username;

        public FollowersPlaceHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.followers_layout);
            avatar = itemView.findViewById(R.id.followers_avatar);
            username = itemView.findViewById(R.id.followers_item_username);
        }
    }
}
