package com.codemaster.fancorner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;

public class ChatScreen extends AppCompatActivity {

    AppBarLayout appBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        appBarLayout = findViewById(R.id.appBar);
        appBarLayout.setExpanded(false);
    }
}