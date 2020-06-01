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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instagramclone.Adapters.ViewHolders.PostsAdapter;
import com.example.instagramclone.Api.UserApi;
import com.example.instagramclone.Common.APIClient;
import com.example.instagramclone.Common.Data;
import com.example.instagramclone.Models.CurrentUserModel;
import com.example.instagramclone.Models.UserModel;
import com.example.instagramclone.Models.UserStatsModel;
import com.example.instagramclone.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFragment extends Fragment {
    private static final String TAG = "UserProfileFragment";

    private RecyclerView postRecycler;
    private ImageView avatar;
    private TextView postCount, followerCount, followingCount, profileName, profileDesc;
    private UserModel user;
    private UserStatsModel userStats;
    private boolean isCurrentUser;

    private UserApi userApi;

    public UserProfileFragment() {
    }

    public UserProfileFragment(boolean isCurrentUser) {
        this.isCurrentUser = isCurrentUser;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        initComponents(view);

        return view;
    }

    private void initComponents(View view) {
        avatar = view.findViewById(R.id.user_profile_avatar);
        postCount = view.findViewById(R.id.user_profile_postCount);
        followerCount = view.findViewById(R.id.user_profile_followerCount);
        followingCount = view.findViewById(R.id.user_profile_followingCount);
        profileName = view.findViewById(R.id.user_profile_name);
        profileDesc = view.findViewById(R.id.user_profile_description);

        userApi = APIClient.getClient().create(UserApi.class);

        postRecycler = view.findViewById(R.id.user_profile_post_recycler);
        GridLayoutManager grid = new GridLayoutManager(view.getContext(), 3);
        postRecycler.setLayoutManager(grid);
        postRecycler.setAdapter(new PostsAdapter(Data.getFeedList()));
        getUserStats();
        initData();
    }

    private void initData() {
        if (isCurrentUser) {
            user = CurrentUserModel.getInstance();
        } else {
            user = Data.getUserList().get(0);
        }
        Picasso.get().load(user.getAvatarURL()).placeholder(R.drawable.default_avatar).into(avatar);
        profileName.setText(user.getFullName());
        profileDesc.setText(user.getBio());

    }

    private void getUserStats() {
        Call<UserStatsModel> call = userApi.getUserStats(getToken());
        call.enqueue(new Callback<UserStatsModel>() {
            @Override
            public void onResponse(Call<UserStatsModel> call, Response<UserStatsModel> response) {
                userStats = response.body();
                setUserStats();
            }

            @Override
            public void onFailure(Call<UserStatsModel> call, Throwable t) {
                Log.d(TAG, "Failed to get stats from database");
            }
        });
    }

    private String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString("JWTtoken", null);
    }

    private void setUserStats() {
        postCount.setText(String.valueOf(userStats.getPostCount()));
        followerCount.setText(String.valueOf(userStats.getFollowerCount()));
        followingCount.setText(String.valueOf(userStats.getFollowingCount()));
    }
}
