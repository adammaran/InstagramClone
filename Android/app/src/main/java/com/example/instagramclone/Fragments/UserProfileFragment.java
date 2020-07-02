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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.instagramclone.Activities.MainActivity;
import com.example.instagramclone.Adapters.ViewHolders.PostsAdapter;
import com.example.instagramclone.Api.UserApi;
import com.example.instagramclone.Common.APIClient;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.Models.UserModel;
import com.example.instagramclone.Models.UserObjModel;
import com.example.instagramclone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFragment extends Fragment {
    private static final String TAG = "UserProfileFragment";

    private RecyclerView postRecycler;
    private ImageView avatar;
    private TextView postCount, followerCount, followingCount, profileName, profileDesc;
    private LinearLayout gotoProfile;

    private UserObjModel user;

    private boolean isCurrenUser = true;
    private String userID;
    private String token;

    private ArrayList<FeedItemModel> currentUserPostList;
    private UserApi userApi;

    public UserProfileFragment() {
    }

    public UserProfileFragment(String token) {
        this.token = token;
    }

    public UserProfileFragment(String userID, boolean isCurrenUser) {
        this.isCurrenUser = isCurrenUser;
        this.userID = userID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        userApi = APIClient.getClient().create(UserApi.class);
        if (isCurrenUser) {
            getCurrentUserObject(view);
        } else {
            getUserByID(view);
        }
        return view;
    }

    private void initComponents(View view) {
        avatar = view.findViewById(R.id.user_profile_avatar);
        postCount = view.findViewById(R.id.user_profile_postCount);
        followerCount = view.findViewById(R.id.user_profile_followerCount);
        followingCount = view.findViewById(R.id.user_profile_followingCount);
        profileName = view.findViewById(R.id.user_profile_name);
        profileDesc = view.findViewById(R.id.user_profile_description);
        postRecycler = view.findViewById(R.id.user_profile_post_recycler);
    }

    private void getUserByID(View v) {
        Log.d(TAG, userID);
        Call<UserObjModel> call = userApi.getUserObject("/api/users/profile/" + userID, getToken());
        call.enqueue(new Callback<UserObjModel>() {
            @Override
            public void onResponse(Call<UserObjModel> call, Response<UserObjModel> response) {
                user = response.body();
                initComponents(v);
                setUserObject();
                getPostList(v);
            }

            @Override
            public void onFailure(Call<UserObjModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getPostList(View v) {
        Call<ArrayList<FeedItemModel>> call = userApi.getCurrentUserPostList("/api/users/posts/" + user.getUser().getUserID(), getToken());
        call.enqueue(new Callback<ArrayList<FeedItemModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FeedItemModel>> call, Response<ArrayList<FeedItemModel>> response) {
                currentUserPostList = response.body();
                initData(v);
            }

            @Override
            public void onFailure(Call<ArrayList<FeedItemModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initData(View view) {
        GridLayoutManager grid = new GridLayoutManager(view.getContext(), 3);
        postRecycler.setLayoutManager(grid);
        postRecycler.setAdapter(new PostsAdapter(currentUserPostList));

    }

    private void getCurrentUserObject(View v) {
        Call<UserObjModel> call = userApi.getCurrentUser(this.token);
        call.enqueue(new Callback<UserObjModel>() {
            @Override
            public void onResponse(Call<UserObjModel> call, Response<UserObjModel> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    initComponents(v);
                    setUserObject();
                    getPostList(v);
                }
            }

            @Override
            public void onFailure(Call<UserObjModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setUserObject() {
        Picasso.get().load(user.getUser().getAvatarURL()).placeholder(R.drawable.default_avatar).into(avatar);
        profileName.setText(user.getUser().getFullName());
        profileDesc.setText(user.getUser().getBio());

        postCount.setText(String.valueOf(user.getUserStats().getPostCount()));
        followerCount.setText(String.valueOf(user.getUserStats().getFollowerCount()));
        followingCount.setText(String.valueOf(user.getUserStats().getFollowingCount()));
    }

    private String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString("JWTtoken", null);
    }
}
