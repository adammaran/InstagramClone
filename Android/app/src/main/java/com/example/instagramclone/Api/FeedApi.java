package com.example.instagramclone.Api;

import com.example.instagramclone.Models.FeedItemModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface FeedApi {

    @GET("posts/feed/")
    Call<List<FeedItemModel>> getFeed(@Header("header-token") String header);
}
