package com.example.instagramclone.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.instagramclone.Api.FeedApi;
import com.example.instagramclone.Common.APIClient;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {
    private static final String TAG = "AddPostActivity";
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private ImageView image;
    private EditText locationText, captionText;
    private TextView share;

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
        share.setOnClickListener(view -> {
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
            postItem.setLocation(location);
            locationText.setText(location.getName() + ", " + location.getAddess());
        }

        editLocationListener();
    }

    private void uploadPost() {
        ;
        postItem.setImageFile(new File(imageUri.getPath()));
        System.out.println(new File(imageUri.getPath()) + " ja sam mali fajl");
        postItem.setDescription(captionText.getText().toString());
        Call<FeedItemModel> call = feedApi.postFeedItem(PreferenceManager.getDefaultSharedPreferences(this).getString("JWTtoken", null), postItem);
        call.enqueue(new Callback<FeedItemModel>() {
            @Override
            public void onResponse(Call<FeedItemModel> call, Response<FeedItemModel> response) {
                Log.d(TAG, "image uploaded");
            }

            @Override
            public void onFailure(Call<FeedItemModel> call, Throwable t) {
                Log.d(TAG, "Error while uploding image");
            }
        });
    }
}