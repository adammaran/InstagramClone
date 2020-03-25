package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.instagramclone.Fragments.FeedFragment;
import com.example.instagramclone.Fragments.UserProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
//                        bottomMenu.findItem(R.id.action_add).setIcon(R.drawable.ic_add_box_gray_unselected);
                        //todo add add fragment and icon
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

}
