package com.example.instagramclone.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagramclone.Adapters.ViewHolders.FeedAdapter;
import com.example.instagramclone.Common.Data;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.R;

import java.util.ArrayList;

import retrofit2.Call;

public class FeedFragment extends Fragment {

    private RecyclerView feedRecycler;

    public FeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        initComponents(v);
        initData(v);
        return v;
    }

    private void initComponents(View view) {
        feedRecycler = view.findViewById(R.id.feed_recycler);
    }

    private void initData(View view) {
        feedRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        FeedAdapter adapter = new FeedAdapter(Data.getFeedList(), getContext());
        feedRecycler.setAdapter(adapter);
    }

//    private ArrayList<FeedItemModel> getFeedList() {
//
//    }
}
