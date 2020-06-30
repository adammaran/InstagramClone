package com.example.instagramclone.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.instagramclone.Fragments.FeedFragment;
import com.example.instagramclone.Fragments.UserProfileFragment;
import com.example.instagramclone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int PICK_IMAGE = 100;

    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout bottomSheet;
    private LinearLayout addFromCamera;
    private LinearLayout addFromGallery;

    private ImageView menuButton;

    private BottomNavigationView bottomNavigationView;
    private Menu bottomMenu;

    private boolean sheetOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        bottomNavigationView = findViewById(R.id.feed_bottomNav);
        bottomMenu = bottomNavigationView.getMenu();
        bottomMenu.getItem(0).setIcon(R.drawable.ic_home_gray_selected);
        bottomSheet = findViewById(R.id.main_add_sheet);
        addFromCamera = findViewById(R.id.add_from_camera_button);
        addFromGallery = findViewById(R.id.add_from_gallery_button);
        menuButton = findViewById(R.id.feed_toolbar_menu);
        loadFragment(new FeedFragment());
        initData();
    }

    private void initData() {
        initBottomNavListener();
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN && sheetOpened) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    sheetOpened = false;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        addFromCamera.setOnClickListener(view -> {
            //Todo add camera here
        });

        addFromGallery.setOnClickListener(view -> openGallery());

        menuButton.setOnClickListener(view -> {

        });

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void startAddPost(String uri) {
        Intent intent = new Intent(this, AddPostActivity.class);
        intent.putExtra("imageUri", uri);
        startActivity(intent);
    }

    private void initBottomNavListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            setUnselectedNav();
            switch (item.getItemId()) {
                case R.id.action_feed:
                    if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_home_gray_selected).getConstantState())) {
                        bottomMenu.findItem(R.id.action_feed).setIcon(R.drawable.ic_home_gray_unselected);
                        findViewById(R.id.feed_toolbar_messages).setVisibility(View.VISIBLE);
                        findViewById(R.id.feed_toolbar_menu).setVisibility(View.GONE);
                    } else {
                        bottomMenu.findItem(R.id.action_feed).setIcon(R.drawable.ic_home_gray_selected);
                        findViewById(R.id.feed_toolbar_messages).setVisibility(View.VISIBLE);
                        findViewById(R.id.feed_toolbar_menu).setVisibility(View.GONE);
                    }
                    return loadFragment(new FeedFragment());
                case R.id.action_search:
                    bottomMenu.findItem(R.id.action_search).setIcon(R.drawable.ic_search_gray_unseleted);
                    //todo add search fragment and icon
                    break;
                case R.id.action_add:
                    setSheetState();
                    break;
                case R.id.action_notifications:
                    if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_notifications_gary_unselected).getConstantState()))
                        bottomMenu.findItem(R.id.action_notifications).setIcon(R.drawable.ic_notifications_gray_selected);
                    else
                        bottomMenu.findItem(R.id.action_notifications).setIcon(R.drawable.ic_notifications_gary_unselected);
                    //todo add notification fragment and icon
                    break;
                case R.id.action_userProfile:
                    if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_person_gray_unselected).getConstantState())) {
                        bottomMenu.findItem(R.id.action_userProfile).setIcon(R.drawable.ic_person_gray_selected);
                        findViewById(R.id.feed_toolbar_menu).setVisibility(View.VISIBLE);
                        findViewById(R.id.feed_toolbar_messages).setVisibility(View.GONE);
                    } else {
                        bottomMenu.findItem(R.id.action_userProfile).setIcon(R.drawable.ic_person_gray_unselected);
                        findViewById(R.id.feed_toolbar_menu).setVisibility(View.VISIBLE);
                        findViewById(R.id.feed_toolbar_messages).setVisibility(View.GONE);
                    }
                    //todo add user fragment and icon
                    loadFragment(new UserProfileFragment(PreferenceManager.getDefaultSharedPreferences(this).getString("JWTtoken", null)));
                    break;
            }
            return false;
        });
    }

    private void setSheetState() {
        if (!sheetOpened) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    private void setUnselectedNav() {
        //todo fix this bad logic!
        bottomMenu.findItem(R.id.action_feed).setIcon(R.drawable.ic_home_gray_unselected);
        bottomMenu.findItem(R.id.action_search).setIcon(R.drawable.ic_search_gray_unseleted);
        bottomMenu.findItem(R.id.action_notifications).setIcon(R.drawable.ic_notifications_gary_unselected);
        bottomMenu.findItem(R.id.action_userProfile).setIcon(R.drawable.ic_person_gray_unselected);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            startAddPost(data.getData().toString());
        }
    }
}
