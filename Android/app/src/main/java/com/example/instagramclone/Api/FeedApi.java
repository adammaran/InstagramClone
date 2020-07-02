package com.example.instagramclone.Api;

import com.example.instagramclone.Models.FeedItemModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface FeedApi {

    @GET
    Call<ArrayList<FeedItemModel>> getFeed(@Url String url, @Header("Authorization") String header);

    @GET
    Call<ArrayList<FeedItemModel>> getExploreFeed(@Url String url, @Header("Authorization") String header);

    @Multipart
    @POST("posts/create/")
    Call<FeedItemModel> postFeedItem(@Header("Authorization") String header, @Part MultipartBody.Part file, @Part("description") RequestBody feedItem, @Part("location") RequestBody location);

    @DELETE
    Call<ResponseBody> deletePost(@Url String url, @Header("Authorization") String header);

    @PATCH
    Call<FeedItemModel> likePost(@Url String url, @Header("Authorization") String header);

    @PATCH
    Call<FeedItemModel> unlikePost(@Url String url, @Header("Authorization") String header);

    @GET
    Call<Integer> getPostLikeCount(@Url String url, @Header("Authorization") String header);
}