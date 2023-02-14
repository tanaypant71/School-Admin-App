package com.example.schooladminapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView schoolcode = findViewById(R.id.schoolcode);
        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);

        Button loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(view -> {
            if (schoolcode.getText().toString().equals("1") && username.getText().toString().equals("1") && password.getText().toString().equals("1")){
                Intent intent = new Intent(MainActivity.this,Main_menu.class);
                startActivity(intent);
                finish();
            }

        });
    }

}