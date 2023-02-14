package com.example.schooladminapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.HashMap;

public class UploadPdfActivity extends AppCompatActivity {

    private final int REQ = 1;
    private Uri pdfData;
    CardView addPdf;
    private EditText pdfTitle;
    private Button uploadPdfBtn;
    private TextView pdfTextView;
    private String pdfName,title;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(this);


        addPdf = findViewById(R.id.addPdf);
        uploadPdfBtn = findViewById(R.id.uploadPdfBtn);
        pdfTitle = findViewById(R.id.pdfTitle);
        pdfTextView = findViewById(R.id.pdfTextView);

        uploadPdfBtn.setOnClickListener(view -> {
            title = pdfTitle.getText().toString();
            if (title.isEmpty()){
                pdfTitle.setError("Empty");
                pdfTitle.requestFocus();
            }else if (pdfData == null){
                Toast.makeText(UploadPdfActivity.this, "Please Upload PDF", Toast.LENGTH_SHORT).show();
            }else{
                uploadpdf();
            }
        });

        addPdf.setOnClickListener(view -> openGallery());
    }

    private void uploadpdf() {
        pd.setTitle("Please Wait....");
        pd.setMessage("Uploading pdf");
        pd.show();
        StorageReference reference = storageReference.child("pdf/"+ pdfName +"-"+ System.currentTimeMillis() + ".pdf");
        reference.putFile(pdfData)
                .addOnSuccessListener(taskSnapshot -> {
                     Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isComplete());
                    Uri uri = uriTask.getResult();
                    uploadData(String.valueOf(uri));
                }).addOnFailureListener(e -> {
                    pd.dismiss();
                    Toast.makeText(UploadPdfActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                });
    }

    private void uploadData(String downloadUrl){
        String uniqueKey = databaseReference.child("pdf").push().getKey();

        HashMap data = new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downloadUrl);

        databaseReference.child("pdf").child(uniqueKey).setValue(data).addOnCompleteListener(task -> {
            pd.dismiss();
            Toast.makeText(UploadPdfActivity.this, "PDF uploaded successfully", Toast.LENGTH_SHORT).show();
            pdfTitle.setText("");
        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(UploadPdfActivity.this, "Failed to Upload PDF", Toast.LENGTH_SHORT).show();
        });

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF File"),REQ);
    }


    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            assert data != null;
            pdfData = data.getData();

            if (pdfData.toString().startsWith("content://")){
                Cursor cursor = null;
                try {
                    cursor = UploadPdfActivity.this.getContentResolver().query(pdfData,null,null,null,null);
                    if (cursor != null && cursor.moveToFirst()) {
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (pdfData.toString().startsWith("file://")){
                pdfName = new File(pdfData.toString()).getName();
            }
            pdfTextView.setText(pdfName);
        }
    }
}