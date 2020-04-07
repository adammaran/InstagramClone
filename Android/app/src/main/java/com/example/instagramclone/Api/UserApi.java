package com.example.instagramclone.Api;

import com.example.instagramclone.Models.TokenModel;
import com.example.instagramclone.Models.UserModel;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("users/")
    Call<TokenModel> postUser(@Body UserModel user);
}
