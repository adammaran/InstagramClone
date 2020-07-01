package com.example.instagramclone.Api;

import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.Models.TokenModel;
import com.example.instagramclone.Models.UserModel;
import com.example.instagramclone.Models.UserObjModel;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface UserApi {

    @POST("users/")
    Call<TokenModel> postUser(@Body UserModel user);

    @POST("auth/login/")
    Call<TokenModel> getUser(@Body UserModel user);

    @GET("users/profile")
    Call<UserObjModel> getCurrentUser(@Header("Authorization") String header);

    @GET
    Call<UserModel> getUserObject(@Url String url, @Header("Authorization") String header);

    @GET
    Call<ArrayList<FeedItemModel>> getCurrentUserPostList(@Url String url, @Header("Authorization") String header);
}
