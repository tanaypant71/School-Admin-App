package com.example.schooladminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.schooladminapp.faculty.UpdateFaculty;
import com.example.schooladminapp.faculty.notice.DeleteNoticeActivity;
import com.example.schooladminapp.faculty.notice.Upload_Notice;

public class Main_menu extends AppCompatActivity implements View.OnClickListener{

    CardView uploadnotice,addgalleryimage,addEbook,faculty,deleteNotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        uploadnotice = findViewById(R.id.addnotice);
        addgalleryimage = findViewById(R.id.addgalleryimage);
        addEbook = findViewById(R.id.addEbook);
        deleteNotice = findViewById(R.id.deleteNotice);
        faculty = findViewById(R.id.faculty);

        uploadnotice.setOnClickListener(this);
        addgalleryimage.setOnClickListener(this);
        addEbook.setOnClickListener(this);
        deleteNotice.setOnClickListener(this);
        faculty.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addnotice) {
            Intent intent = new Intent(Main_menu.this, Upload_Notice.class);
            startActivity(intent);
        } else if (view.getId() == R.id.addgalleryimage) {
            Intent intent = new Intent(Main_menu.this, Upload_Image.class);
            startActivity(intent);
        } else if (view.getId() == R.id.addEbook) {
            Intent intent = new Intent(Main_menu.this, UploadPdfActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.faculty) {
            Intent intent = new Intent(Main_menu.this, UpdateFaculty.class);
            startActivity(intent);
        } else if (view.getId() == R.id.deleteNotice) {
            Intent intent = new Intent(Main_menu.this, DeleteNoticeActivity.class);
            startActivity(intent);
        }
    }
}