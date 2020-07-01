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
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.Api.FeedApi;
import com.example.instagramclone.Common.APIClient;
import com.example.instagramclone.Common.Data;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private TextView description;
    private TextView time;
    private ImageView options;

    private Context context;
    private FeedItemModel itemModel;
    private FeedApi feedApi;

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
        options = view.findViewById(R.id.feed_item_more);
        context = view.getContext();

        feedApi = APIClient.getClient().create(FeedApi.class);
    }

    public void bindItem(FeedItemModel feedItem, Context context) {
        this.itemModel = feedItem;
        Log.d("TEMP", "added post in feed");
//        Picasso.get().load(feedItem.getUser().getAvatarURL()).placeholder(R.drawable.default_avatar).into(avatar);
        username.setText(feedItem.getFullname());
        location.setText(feedItem.getLocationString());

        Bitmap bitmap = BitmapFactory.decodeByteArray(feedItem.getImageBuffer().getData(), 0, feedItem.getImageBuffer().getData().length);
        image.setImageBitmap(bitmap);

        likeCount.setText(feedItem.getLikeCount() + " likes");
        description.setText(feedItem.getDescription());
        time.setText(feedItem.getTimestamp());
        options.setOnClickListener(view -> {
            showPopupMenu(this.context);
        });
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
