package com.example.instagramclone.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView signUpText;

    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        initData();
    }

    private void initComponents() {
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_btn_login);
        signUpText = findViewById(R.id.login_text_signin);
    }

    private void initData() {
        userApi = APIClient.getClient().create(UserApi.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserInfo();
            }
        });
        signUpText.setOnClickListener(view -> startRegistrationActivity());
    }

    private void checkUserInfo() {
        UserModel user = new UserModel(username.getText().toString(), password.getText().toString());
        Call<TokenModel> call = userApi.getUser(user);
        call.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.isSuccessful()) {
                    storeToken(response.body().getToken());
                    CurrentUserModel.setInstance(response.body().getCurrentUserModel());
                    startMainActivity();
                } else {
                    showMessage("Incorrect email or password", 12);
                    Log.e(TAG, Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Snackbar.make(findViewById(R.id.login_password), "Something went wrong", Snackbar.LENGTH_LONG).show();
                Log.e(TAG, (t.getMessage()));
            }
        });
    }

    public void showMessage(final String message, final int length) {
        View root = findViewById(android.R.id.content);
        Toast toast = Toast.makeText(this, message, length);
        int yOffset = Math.max(0, root.getHeight() - toast.getYOffset());
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, yOffset);
        toast.show();
    }

    private void storeToken(String token) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("JWTtoken", token).apply();
    }

    private void startRegistrationActivity() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
