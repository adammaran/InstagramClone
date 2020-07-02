package com.example.instagramclone.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instagramclone.Api.UserApi;
import com.example.instagramclone.Common.APIClient;
import com.example.instagramclone.Common.FileUtil;
import com.example.instagramclone.Models.CurrentUserModel;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.Models.UserModel;
import com.example.instagramclone.Models.UserObjModel;
import com.example.instagramclone.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";
    private static final int PICK_IMAGE = 100;

    private ImageView cancel, done, avatar;
    private TextView editAvatarText, fullname, username, description;

    private UserApi userApi;

    private boolean newAvatar = false;

    private String avatarUri;

    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initComponents();
    }

    private void initComponents() {
        cancel = findViewById(R.id.edit_profile_toolbar_cancel);
        done = findViewById(R.id.edit_profile_toolbar_done);
        avatar = findViewById(R.id.edit_profile_avatar);
        editAvatarText = findViewById(R.id.edit_avatar_text);
        fullname = findViewById(R.id.edit_text_name);
        username = findViewById(R.id.edit_text_username);
        description = findViewById(R.id.edit_text_description);
        userApi = APIClient.getClient().create(UserApi.class);

        initData();
    }

    private void initData() {
        user = CurrentUserModel.getInstance();
        fullname.setText(user.getFullName());
        username.setText(user.getUserName());
        description.setText(user.getBio());
        if (user.getAvatarURL() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getAvatarURL().getData(), 0, user.getAvatarURL().getData().length);
            avatar.setImageBitmap(bitmap);
        }

        avatar.setOnClickListener(view -> {
            openGallery();
        });
        editAvatarText.setOnClickListener(view -> {
            openGallery();
        });

        done.setOnClickListener(view -> {
            uploadEditedUser();
            if (this.newAvatar)
                uploadEditedAvatar();
        });

        cancel.setOnClickListener(view -> {
            startMainActivity();
        });
    }

    private UserModel createEditedUser() {
        UserModel newUser = new UserModel();
        if (!user.getUserName().equals(username.getText().toString())) {
            user.setUserName(username.getText().toString());
            newUser.setUserName(username.getText().toString());
        } else if (!user.getFullName().equals(fullname.getText().toString())) {
            user.setFullName(fullname.getText().toString());
            newUser.setFullName(fullname.getText().toString());
        } else if (!user.getBio().equals(description.getText().toString())) {
            user.setBio(description.getText().toString());
            newUser.setBio(description.getText().toString());
        }
        return newUser;
    }

    private void updateUserObject() {
        user.setUserName(fullname.getText().toString());
        user.setUserName(username.getText().toString());
        user.setBio(description.getText().toString());
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void uploadEditedUser() {
        updateUserObject();
        Call<UserModel> call = userApi.editUser(getToken(), createEditedUser());
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                CurrentUserModel.setInstance(user);
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }

    private void uploadEditedAvatar() {
        File file = null;
        try {
            file = FileUtil.from(EditProfileActivity.this, Uri.parse(avatarUri));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(Uri.parse(avatarUri))), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

        Call<UserModel> call = userApi.postAvatar(getToken(), body);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                startMainActivity();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d(TAG, "NES SAM SJEBAO");
            }
        });
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            this.newAvatar = true;
            this.avatarUri = data.getData().toString();
            setAvatarImage();
        }
    }

    private void setAvatarImage() {
        avatar.setImageURI(Uri.parse(avatarUri));
    }

    private String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("JWTtoken", null);
    }
}
