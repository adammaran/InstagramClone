package com.example.instagramclone.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.instagramclone.R;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private EditText commentText;
    private Button postComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initComponents();
    }

    private void initComponents() {
        recycler = findViewById(R.id.comments_recycler);
        commentText = findViewById(R.id.comments_editText);
        postComment = findViewById(R.id.comments_postComment);
    }

    private void initData(){
        
    }
}
