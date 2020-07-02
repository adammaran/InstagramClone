package com.example.instagramclone.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagramclone.Adapters.ViewHolders.FollowersAdapter;
import com.example.instagramclone.Api.UserApi;
import com.example.instagramclone.Common.APIClient;
import com.example.instagramclone.Models.FollowerModel;
import com.example.instagramclone.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersFragment extends Fragment {

    private RecyclerView recycler;
    private int type;
    private String userID;
    private ArrayList<FollowerModel> list;

    private UserApi userApi;

    public FollowersFragment() {

    }

    public FollowersFragment(String userID, int type) {
        this.userID = userID;
        this.type = type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        userApi = APIClient.getClient().create(UserApi.class);
        recycler = view.findViewById(R.id.followers_recycler);
        if (type == 0) {
            getFollowerList();
        } else {
            getFollowingList();
        }
        initData(view);
    }

    private void getFollowerList() {
        Call<ArrayList<FollowerModel>> call = userApi.getFollowerList("/api/followers/" + userID, getToken());
        call.enqueue(new Callback<ArrayList<FollowerModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FollowerModel>> call, Response<ArrayList<FollowerModel>> response) {
                if (response.isSuccessful())
                    list = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<FollowerModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getFollowingList() {
        Call<ArrayList<FollowerModel>> call = userApi.getFollowingList("/api/following/" + userID, getToken());
        call.enqueue(new Callback<ArrayList<FollowerModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FollowerModel>> call, Response<ArrayList<FollowerModel>> response) {
                if (response.isSuccessful())
                    list = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<FollowerModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initData(View view) {
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recycler.setAdapter(new FollowersAdapter(list));
    }

    private String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString("JWTtoken", null);
    }
}
