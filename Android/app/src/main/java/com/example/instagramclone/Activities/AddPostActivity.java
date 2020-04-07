package com.example.instagramclone.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.instagramclone.R;

import java.io.FileInputStream;

public class AddPostActivity extends AppCompatActivity {

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        initComponents();
    }

    private void initComponents() {
        image = findViewById(R.id.addPost_image);

        initData();
    }

    private void initData() {
        if (getImageFromDisk() == null) {
            System.out.println("NE RADI OVO SRANJE");
        } else
            image.setImageBitmap(getImageFromDisk());
    }

    private Bitmap getImageFromDisk() {
        try {
            Bitmap bitmap;
            FileInputStream is = this.openFileInput("new_image");
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
