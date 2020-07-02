package com.example.instagramclone.Adapters.ViewHolders;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.Activities.MainActivity;
import com.example.instagramclone.Api.FeedApi;
import com.example.instagramclone.Api.UserApi;
import com.example.instagramclone.Common.APIClient;
import com.example.instagramclone.Common.Data;
import com.example.instagramclone.Fragments.UserProfileFragment;
import com.example.instagramclone.Models.CurrentUserModel;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.Models.UserObjModel;
import com.example.instagramclone.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedPlaceHolder extends RecyclerView.ViewHolder {

    public ImageView avatar;
    private TextView username;
    private TextView location;
    public ImageView image;
    private TextView likeCount;
    private ImageView like;
    private ImageView liked;
    private TextView description;
    private TextView time;
    private ImageView options;
    private LinearLayout gotoProfile;
    private ImageView comments;
    private TextView toComments;

    private UserObjModel user;

    private Context context;
    private FeedItemModel itemModel;
    private FeedApi feedApi;
    private UserApi userApi;

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
        like = view.findViewById(R.id.feed_item_like);
        liked = view.findViewById(R.id.feed_item_liked);
        description = view.findViewById(R.id.feed_item_description);
        time = view.findViewById(R.id.feed_item_timestamp);
        options = view.findViewById(R.id.feed_item_more);
        gotoProfile = view.findViewById(R.id.feed_item_gotoProfile);
        comments = view.findViewById(R.id.feed_item_comment);
        toComments = view.findViewById(R.id.feed_item_to_comments);

        context = view.getContext();

        feedApi = APIClient.getClient().create(FeedApi.class);
        userApi = APIClient.getClient().create(UserApi.class);
    }

    public void bindItem(FeedItemModel feedItem, Context context) {
        this.itemModel = feedItem;
        Log.d("TEMP", "added post in feed");
        username.setText(feedItem.getFullname());
        location.setText(feedItem.getLocationString());

        Bitmap bitmap = BitmapFactory.decodeByteArray(feedItem.getImageBuffer().getData(), 0, feedItem.getImageBuffer().getData().length);
        image.setImageBitmap(bitmap);

//        Bitmap avatarBitmap = BitmapFactory.decodeByteArray(user.getUser().getAvatarURL().getData(), 0, feedItem.getImageBuffer().getData().length);
//        avatar.setImageBitmap(avatarBitmap);


        like.setOnClickListener(view -> {
            addLikeToImage(feedItem.getFeedUUID());
        });

        liked.setOnClickListener(view -> {
            removeLikeFromImage(feedItem.getFeedUUID());
        });

        setLikeCount(feedItem.getFeedUUID());
        description.setText(feedItem.getDescription());
        time.setText(feedItem.getTimestamp());
        if (CurrentUserModel.getInstance().getUserID().equals(feedItem.getUserID())) {
            options.setVisibility(View.VISIBLE);
        }
        options.setOnClickListener(view -> {
            showPopupMenu(this.context);
        });
        gotoProfile.setOnClickListener(view -> {
            MainActivity main = (MainActivity) view.getContext();
            main.loadFragment(new UserProfileFragment(feedItem.getUserID(), false));
        });
    }

    private void setLikeCount(String feedID) {
        Call<Integer> call = feedApi.getPostLikeCount("/api/posts/likes/" + feedID, getToken());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body() != null)
                    likeCount.setText(response.body().toString() + " likes");
                else likeCount.setText("0 likes");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

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

    private void setUnlikeImage() {
        int n = itemModel.getLikeCount() + 1;
        itemModel.setLikeCount(n);
        like.setVisibility(View.VISIBLE);
        liked.setVisibility(View.GONE);
    }

    private void setLikedIcon() {
        like.setVisibility(View.GONE);
        liked.setVisibility(View.VISIBLE);

    }

    private void showPopupMenu(Context context) {
        Log.d("FeedPlaceHolder", "CE GA OTVORI VALJDA");
        PopupMenu popupMenu = new PopupMenu(this.context, options);
        popupMenu.getMenuInflater().inflate(R.menu.feed_options_menu, popupMenu.getMenu());
        popupMenu.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
            showDeleteConfirmDialog(this.context);
            return true;
        });
        popupMenu.show();
    }

    private void showDeleteConfirmDialog(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Delete this post")
                .setMessage("Are you sure you want to delete this post?")
                .setPositiveButton("Confirm", ((dialogInterface, i) -> deletePost()))
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();

        dialog.show();
    }

    private void deletePost() {
        Call<ResponseBody> call = feedApi.deletePost("/api/posts/delete/" + itemModel.getFeedUUID(), getToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //todo add snackbar
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("JWTtoken", null);
    }
}
