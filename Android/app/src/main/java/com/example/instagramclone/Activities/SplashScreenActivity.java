package com.example.instagramclone.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.instagramclone.Api.UserApi;
import com.example.instagramclone.Common.APIClient;
import com.example.instagramclone.Models.CurrentUserModel;
import com.example.instagramclone.Models.UserModel;
import com.example.instagramclone.Models.UserObjModel;
import com.example.instagramclone.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        userApi = APIClient.getClient().create(UserApi.class);
        String token = getToken();
        if (token == null) startLoginActivity();
        else {
            getUserFromDb();
        }
    }

    private void getUserFromDb() {
        Call<UserObjModel> call = userApi.getCurrentUser(getToken());
        call.enqueue(new Callback<UserObjModel>() {
            @Override
            public void onResponse(Call<UserObjModel> call, Response<UserObjModel> response) {
                CurrentUserModel.setInstance(response.body().getUser());
                startMainActivity();
            }

            @Override
            public void onFailure(Call<UserObjModel> call, Throwable t) {

            }
        });
    }

    private String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("JWTtoken", null);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
