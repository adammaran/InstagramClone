package com.example.instagramclone.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import com.example.instagramclone.Fragments.FeedFragment;
import com.example.instagramclone.Fragments.UserProfileFragment;
import com.example.instagramclone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Menu bottomMenu;

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
        loadFragment(new FeedFragment());
        initData();
    }

    private void initData() {
        initBottomNavListener();
    }

    private void initBottomNavListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setUnselectedNav();
                switch (item.getItemId()) {
                    case R.id.action_feed:
                        if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_home_gray_selected).getConstantState()))
                            bottomMenu.findItem(R.id.action_feed).setIcon(R.drawable.ic_home_gray_unselected);
                        else
                            bottomMenu.findItem(R.id.action_feed).setIcon(R.drawable.ic_home_gray_selected);
                        return loadFragment(new FeedFragment());
                    case R.id.action_search:
                        bottomMenu.findItem(R.id.action_search).setIcon(R.drawable.ic_search_gray_unseleted);
                        //todo add search fragment and icon
                        break;
                    case R.id.action_add:
                        startCamera();
                        break;
                    case R.id.action_notifications:
                        if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_notifications_gary_unselected).getConstantState()))
                            bottomMenu.findItem(R.id.action_notifications).setIcon(R.drawable.ic_notifications_gray_selected);
                        else
                            bottomMenu.findItem(R.id.action_notifications).setIcon(R.drawable.ic_notifications_gary_unselected);
                        //todo add notification fragment and icon
                        break;
                    case R.id.action_userProfile:
                        if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_person_gray_unselected).getConstantState()))
                            bottomMenu.findItem(R.id.action_userProfile).setIcon(R.drawable.ic_person_gray_selected);
                        else
                            bottomMenu.findItem(R.id.action_userProfile).setIcon(R.drawable.ic_person_gray_unselected);
                        //todo add user fragment and icon
                        loadFragment(new UserProfileFragment());
                        break;
                }
                return false;
            }
        });
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
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
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        addImageToDisk(bitmap);
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }

    private void addImageToDisk(Bitmap bitmap) {
        try {
            FileOutputStream stream = this.openFileOutput("new_image", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
