package com.example.instagramclone.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.instagramclone.Api.UserApi;
import com.example.instagramclone.Common.APIClient;
import com.example.instagramclone.Models.CurrentUserModel;
import com.example.instagramclone.Models.TokenModel;
import com.example.instagramclone.Models.UserModel;
import com.example.instagramclone.R;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity";

    private EditText email;
    private EditText fullname;
    private EditText username;
    private EditText password;
    private Button signUpButton;

    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initComponents();
        initData();
    }

    private void initComponents() {
        email = findViewById(R.id.registration_email);
        fullname = findViewById(R.id.registration_fullname);
        username = findViewById(R.id.registration_username);
        password = findViewById(R.id.registration_password);
        signUpButton = findViewById(R.id.registration_btn_signup);
    }

    private void initData() {
        userApi = APIClient.getClient().create(UserApi.class);
        signUpButton.setOnClickListener(view -> postUser());
    }


    private void postUser() {
        UserModel user = new UserModel(username.getText().toString(), email.getText().toString(), password.getText().toString(), fullname.getText().toString());
        Call<TokenModel> call = userApi.postUser(user);
        call.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.isSuccessful()) {
                    storeToken(response.body().getToken());
                    CurrentUserModel.setInstance(response.body().getCurrentUserModel());
                    startMainActivity();
                } else {
                    Snackbar.make(findViewById(R.id.registration_password), "Something went wrong", Snackbar.LENGTH_LONG).show();
                    Log.e(TAG, Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Snackbar.make(findViewById(R.id.registration_password), "Something went wrong", Snackbar.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void storeToken(String token) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("JWTtoken", token).apply();
        System.out.println(PreferenceManager.getDefaultSharedPreferences(this).getString("JWTtoken", null) + " a ovaj token");
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
