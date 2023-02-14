package com.example.schooladminapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_screen extends AppCompatActivity {

    //after completion of 2000ms the activity will time out and new activity will get started

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        handler=new Handler();
        handler.postDelayed(() -> {
            Intent intent=new Intent(Splash_screen.this,MainActivity.class);
            startActivity(intent);
            finish();
        },3000);

    }
}