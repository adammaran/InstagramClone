package com.example.instagramclone.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.instagramclone.Activities.MainActivity;
import com.example.instagramclone.Api.FeedApi;
import com.example.instagramclone.Common.APIClient;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnePostFragment extends Fragment {

    private ImageView avatar, image, like, liked, comment;
    private TextView username, location, likeCount, description, time;
    private LinearLayout gotoProfile;

    private FeedApi feedApi;

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
        liked = v.findViewById(R.id.feed_item_liked);
        comment = v.findViewById(R.id.feed_item_comment);

        username = v.findViewById(R.id.feed_item_username);
        location = v.findViewById(R.id.feed_item_location);
        likeCount = v.findViewById(R.id.feed_item_like_count);
        description = v.findViewById(R.id.feed_item_description);
        time = v.findViewById(R.id.feed_item_timestamp);

        feedApi = APIClient.getClient().create(FeedApi.class);

        gotoProfile = v.findViewById(R.id.feed_item_gotoProfile);
        gotoProfile.setOnClickListener(view1 -> {
            MainActivity main = (MainActivity) v.getContext();
            main.loadFragment(new UserProfileFragment(itemModel.getUserID(), false));
        });
        initData();
    }

    private void initData() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(itemModel.getImageBuffer().getData(), 0, itemModel.getImageBuffer().getData().length);
        image.setImageBitmap(bitmap);
        likeCount.setText(itemModel.getLikeCount() + " likes");
        username.setText(itemModel.getFullname());
        location.setText(itemModel.getLocationString());
        description.setText(itemModel.getDescription());

        like.setOnClickListener(view -> {
            addLikeToImage(itemModel.getFeedUUID());
        });

        liked.setOnClickListener(view -> {
            removeLikeFromImage(itemModel.getFeedUUID());
        });
    }

    private void removeLikeFromImage(String feedID) {
        Call<FeedItemModel> call = feedApi.unlikePost("/api/posts/unllike/" + feedID, getToken());
        call.enqueue(new Callback<FeedItemModel>() {
            @Override
            public void onResponse(Call<FeedItemModel> call, Response<FeedItemModel> response) {
                setUnlikeImage();
            }

            @Override
            public void onFailure(Call<FeedItemModel> call, Throwable t) {

            }
        });
    }

    private void addLikeToImage(String feedID) {
        Call<FeedItemModel> call = feedApi.likePost("/api/posts/like/" + feedID, getToken());
        call.enqueue(new Callback<FeedItemModel>() {
            @Override
            public void onResponse(Call<FeedItemModel> call, Response<FeedItemModel> response) {
                setLikedIcon();
            }

            @Override
            public void onFailure(Call<FeedItemModel> call, Throwable t) {

            }
        });
    }

    private void setUnlikeImage() {
        like.setVisibility(View.VISIBLE);
        liked.setVisibility(View.GONE);
    }

    private void setLikedIcon() {
        like.setVisibility(View.GONE);
        liked.setVisibility(View.VISIBLE);

    }

    private String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString("JWTtoken", null);
    }
}
