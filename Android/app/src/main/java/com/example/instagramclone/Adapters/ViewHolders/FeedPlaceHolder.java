package com.example.instagramclone.Adapters.ViewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.R;
import com.squareup.picasso.Picasso;

public class FeedPlaceHolder extends RecyclerView.ViewHolder {

    public ImageView avatar;
    private TextView username;
    private TextView location;
    public ImageView image;
    private TextView likeCount;
    private TextView description;
    private TextView time;

    public FeedPlaceHolder(@NonNull View itemView) {
        super(itemView);
        initComponents(itemView);
    }

    private void initComponents(View view) {
        avatar = view.findViewById(R.id.feed_item_avatar);
        username = view.findViewById(R.id.feed_item_username);
        location = view.findViewById(R.id.feed_item_location);
        image = view.findViewById(R.id.feed_item_image);
        likeCount = view.findViewById(R.id.feed_item_like_count);
        description = view.findViewById(R.id.feed_item_description);
        time = view.findViewById(R.id.feed_item_timestamp);
    }

    public void bindItem(FeedItemModel feedItem, Context context) {
        Picasso.get().load(feedItem.getUser().getAvatarURL()).into(avatar);
        username.setText(feedItem.getUser().getUserName());
        location.setText("Unknown for now");
        Picasso.get().load(feedItem.getImageURL()).into(image);
        likeCount.setText(Integer.toString(feedItem.getLikeCount()));
        description.setText(feedItem.getDescription());
        time.setText("Placeholder");
    }

}
