package com.example.instagramclone.Adapters.ViewHolders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.Common.Data;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.R;
import com.google.gson.Gson;
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
        Log.d("TEMP", "added post in feed");
//        Picasso.get().load(feedItem.getUser().getAvatarURL()).placeholder(R.drawable.default_avatar).into(avatar);
        username.setText(feedItem.getFullname());
        location.setText(feedItem.getLocationString());

        Bitmap bitmap = BitmapFactory.decodeByteArray(feedItem.getImageBuffer().getData(), 0, feedItem.getImageBuffer().getData().length);
        image.setImageBitmap(bitmap);

        likeCount.setText(Integer.toString(feedItem.getLikeCount()));
        description.setText(feedItem.getDescription());
        time.setText("Placeholder");
    }

}
