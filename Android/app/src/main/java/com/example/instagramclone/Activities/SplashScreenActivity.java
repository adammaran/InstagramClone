package com.example.instagramclone.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.instagramclone.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        String token = getToken();
        if (token == null) startLoginActivity();
        else startMainActivity();
    }

    private String getToken() {
        SharedPreferences settings = getSharedPreferences("RegistrationActivity", Context.MODE_PRIVATE);
        return settings.getString("JWTtoken", null);
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
