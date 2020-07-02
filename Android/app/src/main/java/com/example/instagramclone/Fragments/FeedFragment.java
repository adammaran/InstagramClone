package com.example.instagramclone.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.instagramclone.Adapters.ViewHolders.FeedAdapter;
import com.example.instagramclone.Api.FeedApi;
import com.example.instagramclone.Common.APIClient;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedFragment extends Fragment {
    private static final String TAG = "FeedFragment";

    private RecyclerView feedRecycler;
    private FeedApi feedApi;
    private ArrayList<FeedItemModel> feedList;
    private ProgressBar progressBar;

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
        getFeedList(v);
        return v;
    }

    private void initComponents(View view) {
        feedRecycler = view.findViewById(R.id.feed_recycler);
        progressBar = view.findViewById(R.id.feed_progressBar);
        feedApi = APIClient.getClient().create(FeedApi.class);
    }

    private void initData(View v) {
        progressBar.setVisibility(View.GONE);
        feedRecycler.setLayoutManager(new LinearLayoutManager(v.getContext()));
        FeedAdapter adapter = new FeedAdapter(feedList, v.getContext());
        feedRecycler.setAdapter(adapter);
    }

    private void getFeedList(View v) {
        Call<ArrayList<FeedItemModel>> call = feedApi.getFeed("/api/posts/feed/1", getToken());
        call.enqueue(new Callback<ArrayList<FeedItemModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FeedItemModel>> call, Response<ArrayList<FeedItemModel>> response) {
                feedList = response.body();
                Collections.reverse(feedList);
                Log.d(TAG, getToken());
                initData(v);
            }

            @Override
            public void onFailure(Call<ArrayList<FeedItemModel>> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    private String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString("JWTtoken", null);
    }
}
