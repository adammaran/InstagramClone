package com.example.instagramclone.Api;

import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.Models.FollowerModel;
import com.example.instagramclone.Models.TokenModel;
import com.example.instagramclone.Models.UserModel;
import com.example.instagramclone.Models.UserObjModel;


import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface UserApi {

    @POST("users/")
    Call<TokenModel> postUser(@Body UserModel user);

    @POST("auth/login/")
    Call<TokenModel> getUser(@Body UserModel user);

    @GET("users/profile")
    Call<UserObjModel> getCurrentUser(@Header("Authorization") String header);

    @GET
    Call<UserObjModel> getUserObject(@Url String url, @Header("Authorization") String header);

    @GET
    Call<ArrayList<FeedItemModel>> getCurrentUserPostList(@Url String url, @Header("Authorization") String header);

    @Multipart
    @PATCH("users/avatar")
    Call<UserModel> postAvatar(@Header("Authorization") String header, @Part MultipartBody.Part file);

    @GET
    Call<ArrayList<FollowerModel>> getFollowingList(@Url String url, @Header("Authorization") String header);

    @GET
    Call<ArrayList<FollowerModel>> getFollowerList(@Url String url, @Header("Authorization") String header);

    @PATCH
    Call<String> followUser(@Url String url, @Header("Authorization") String header);

    @PATCH
    Call<String> unfollowUser(@Url String url, @Header("Authorization") String header);

    @PATCH("users/edit")
    Call<UserModel> editUser(@Header("Authorization") String header, @Body UserModel userModel);

    @GET
    Call<UserObjModel> getUserAvatar(@Url String url, @Header("Authorization") String header);
}
