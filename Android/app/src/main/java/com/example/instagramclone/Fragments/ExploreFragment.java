package com.example.instagramclone.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.instagramclone.Adapters.ViewHolders.PostsAdapter;
import com.example.instagramclone.Api.FeedApi;
import com.example.instagramclone.Common.APIClient;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment {
    private static final String TAG = "ExploreFragment";

    private ArrayList<FeedItemModel> exploreList;

    private RecyclerView exploreRecycler;
    private ProgressBar progressBar;

    private FeedApi feedApi;

    public ExploreFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_explore, container, false);
        initComponents(v);
        return v;
    }

    private void initComponents(View v) {
        exploreRecycler = v.findViewById(R.id.explore_recycler);
        progressBar = v.findViewById(R.id.explore_progressBar);
        feedApi = APIClient.getClient().create(FeedApi.class);

        getExploreList(v);
    }

    private void initData(View v) {
        progressBar.setVisibility(View.GONE);
        exploreRecycler.setLayoutManager(new GridLayoutManager(v.getContext(), 3));
        exploreRecycler.setAdapter(new PostsAdapter(exploreList));
    }

    private void getExploreList(View v) {
        Call<ArrayList<FeedItemModel>> call = feedApi.getExploreFeed("/api/posts/explore/1", getToken());
        call.enqueue(new Callback<ArrayList<FeedItemModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FeedItemModel>> call, Response<ArrayList<FeedItemModel>> response) {
                exploreList = response.body();
                initData(v);
            }

            @Override
            public void onFailure(Call<ArrayList<FeedItemModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString("JWTtoken", null);
    }
}
