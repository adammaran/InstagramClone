package com.example.instagramclone.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.instagramclone.Api.FeedApi;
import com.example.instagramclone.Common.APIClient;
import com.example.instagramclone.Common.FileUtil;
import com.example.instagramclone.Models.FeedItemModel;
import com.example.instagramclone.Models.LocationModel;
import com.example.instagramclone.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {
    private static final String TAG = "AddPostActivity";
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private ImageView image;
    private EditText locationText, captionText;
    private TextView share;
    private ProgressBar progressBar;

    private Uri imageUri;
    private FeedApi feedApi;

    private FeedItemModel postItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Places.initialize(getApplicationContext(), "AIzaSyCm9w5hmifYrImzBrGzt_4UMcRWFteW_P0");
        initComponents();
    }

    private void initComponents() {
        image = findViewById(R.id.addPost_image);
        locationText = findViewById(R.id.addPost_place);
        captionText = findViewById(R.id.addPost_caption);
        share = findViewById(R.id.addPost_done);
        progressBar = findViewById(R.id.addPost_progressBar);
        share.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            uploadPost();
        });
        postItem = new FeedItemModel();
        editLocationListener();
        initData();
    }

    private void initData() {
        imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
        image.setImageURI(imageUri);
        feedApi = APIClient.getClient().create(FeedApi.class);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void editLocationListener() {
        locationText.setOnTouchListener((view, motionEvent) -> {
            showLocationDialog();
            view.setOnTouchListener(null);
            return false;
        });
    }

    private void showLocationDialog() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("RS")
                .build(this);

        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            LocationModel location = new LocationModel(place.getName(), place.getAddress(), place.getLatLng().latitude, place.getLatLng().longitude);
            postItem.setLocationString(location.getName() + ", " + location.getAddess());
            locationText.setText(location.getName() + ", " + location.getAddess());
        }

        editLocationListener();
    }

    private void uploadPost() {
        File file = null;
        try {
            file = FileUtil.from(AddPostActivity.this, imageUri);
            System.out.println(file + " ja sam mali fajl cao");
            Log.d(TAG, "File added");
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody description = RequestBody.create(MultipartBody.FORM, captionText.getText().toString());

        postItem.setDescription(captionText.getText().toString());
        Log.d(TAG, postItem.getLocationString());
        Call<FeedItemModel> call = feedApi.postFeedItem(getToken(), body, description);
        call.enqueue(new Callback<FeedItemModel>() {
            @Override
            public void onResponse(Call<FeedItemModel> call, Response<FeedItemModel> response) {
                Log.d(TAG, "image uploaded");
                new MainActivity().startActivity(new Intent(getBaseContext(), MainActivity.class));
            }

            @Override
            public void onFailure(Call<FeedItemModel> call, Throwable t) {
                Log.d(TAG, "Error while uploading image");
                new MainActivity().startActivity(new Intent(getBaseContext(), MainActivity.class));
                t.printStackTrace();
            }
        });
    }

    private String getToken() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("JWTtoken", null);
    }
}