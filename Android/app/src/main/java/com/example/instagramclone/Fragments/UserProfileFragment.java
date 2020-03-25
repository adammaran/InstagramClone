package com.example.instagramclone.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instagramclone.Adapters.ViewHolders.PostsAdapter;
import com.example.instagramclone.Common.Data;
import com.example.instagramclone.Models.UserModel;
import com.example.instagramclone.R;
import com.squareup.picasso.Picasso;

public class UserProfileFragment extends Fragment {

    private RecyclerView postRecycler;
    private ImageView avatar;
    private TextView postCount, followerCount, followingCount, profileName, profileDesc;

    public UserProfileFragment() {
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

        postRecycler = view.findViewById(R.id.user_profile_post_recycler);
        GridLayoutManager grid = new GridLayoutManager(view.getContext(), 3);
        postRecycler.setLayoutManager(grid);
        postRecycler.setAdapter(new PostsAdapter(Data.getFeedList()));
        initData();
    }

    private void initData(){
        UserModel user = Data.getUserList().get(0);

        Picasso.get().load(user.getAvatarURL()).into(avatar);
        postCount.setText(String.valueOf(user.getPostCount()));
        followerCount.setText(String.valueOf(user.getFollowerCount()));
        followingCount.setText(String.valueOf(user.getFollowingCount()));
        profileName.setText(user.getFullName());
        profileDesc.setText(user.getBio());

    }
}
