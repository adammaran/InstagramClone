package com.example.instagramclone.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.R;

public class OnePostFragment extends Fragment {

    private ImageView avatar, options, image, like, comment;
    private TextView username, location, likeCount, description, time;

    private FeedItemModel itemModel;

    public OnePostFragment() {
    }

    public OnePostFragment(FeedItemModel feedItemModel) {
        this.itemModel = feedItemModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one_post, container, false);
        initComponents(v);
        return v;
    }

    private void initComponents(View v) {
        avatar = v.findViewById(R.id.feed_item_avatar);
        image = v.findViewById(R.id.feed_item_image);
        like = v.findViewById(R.id.feed_item_like);
        comment = v.findViewById(R.id.feed_item_comment);

        username = v.findViewById(R.id.feed_item_username);
        location = v.findViewById(R.id.feed_item_location);
        likeCount = v.findViewById(R.id.feed_item_like_count);
        description = v.findViewById(R.id.feed_item_description);
        time = v.findViewById(R.id.feed_item_timestamp);
        initData();
    }

    private void initData() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(itemModel.getImageBuffer().getData(), 0, itemModel.getImageBuffer().getData().length);
        image.setImageBitmap(bitmap);

        likeCount.setText(itemModel.getLikeCount() + " likes");
        username.setText(itemModel.getFullname());
        location.setText(itemModel.getLocationString());
        description.setText(itemModel.getDescription());
    }
}
