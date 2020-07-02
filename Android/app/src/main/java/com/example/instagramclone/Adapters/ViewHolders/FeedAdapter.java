package com.example.instagramclone.Adapters.ViewHolders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.R;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedPlaceHolder> {

    private ArrayList<FeedItemModel> feedList;
    private Context context;

    public FeedAdapter(ArrayList<FeedItemModel> feedList, Context context) {
        this.feedList = feedList;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_post, parent, false);
        return new FeedPlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedPlaceHolder holder, int position) {
        holder.bindItem(feedList.get(position), context);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }
}
