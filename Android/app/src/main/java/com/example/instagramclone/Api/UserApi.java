package com.example.instagramclone.Api;

import com.example.instagramclone.Models.TokenModel;
import com.example.instagramclone.Models.UserModel;
import com.example.instagramclone.Models.UserStatsModel;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApi {

    @POST("users/")
    Call<TokenModel> postUser(@Body UserModel user);

    @POST("auth/login/")
    Call<TokenModel> getUser(@Body UserModel user);

    @GET("users/stats/")
    Call<UserStatsModel> getUserStats(@Header("Authorization") String token);

    @GET("users/")
    Call<UserModel> getCurrentUser(@Header("Authorization") String token);
}
