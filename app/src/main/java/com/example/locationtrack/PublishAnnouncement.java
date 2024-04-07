package com.example.locationtrack;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PublishAnnouncement extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_CODE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    TextInputLayout announce;
    Button chooseFileBtn, takePhotoBtn, annBtn;
    DatabaseReference databaseReference;
    DatabaseReference ref2;
    StorageReference storageReference;
    ProgressBar progressBar;
    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_announcement);

        announce = findViewById(R.id.announce);
        chooseFileBtn = findViewById(R.id.chooseFileBtn);
        takePhotoBtn = findViewById(R.id.takePhotoBtn);
        annBtn = findViewById(R.id.annBtn);
        progressBar = findViewById(R.id.progressBar);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Announcement");
        ref2 = FirebaseDatabase.getInstance().getReference().child("Text Announcements");
        storageReference = FirebaseStorage.getInstance().getReference().child("uploads");

        chooseFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilePicker();
            }
        });

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        annBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String announcementText = announce.getEditText().getText().toString();
                pushAnnouncement(announcementText);
                if (fileUri != null) {
                    uploadFileToStorage(fileUri);
                }
            }
        });

        // Request permissions if not granted
        if (!checkPermissions()) {
            requestPermissions();
        }
    }

    private void pushAnnouncement(String announcementText) {
        if (!announcementText.trim().isEmpty()) {
            AnnounceModel announceModel = new AnnounceModel(announcementText);
            ref2.push().setValue(announceModel);
            announce.getEditText().setText("");
            Toast.makeText(this, "Text Announcement uploaded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter an announcement text", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf"); // Filter for PDF files
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_FILE_REQUEST_CODE) {
                if (data.getData() != null) {
                    fileUri = data.getData();
                    String fileName = getFileName(fileUri);
                    chooseFileBtn.setText(fileName); // Set the button text to the file name
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    fileUri = saveImageToStorage((Uri) extras.get(MediaStore.EXTRA_OUTPUT));
                }
            }
        }
    }

    private Uri saveImageToStorage(Uri photoUri) {
        return photoUri;
    }

    private void uploadFileToStorage(Uri fileUri) {
        progressBar.setVisibility(View.VISIBLE);
        if (fileUri != null) {
            StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(fileUri));

            fileRef.putFile(fileUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Get the download URL
                        fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Upload the download URL to Firebase database
                            String downloadUrl = uri.toString();
                            databaseReference.push().setValue(downloadUrl);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(this, "File Announcement uploaded", Toast.LENGTH_SHORT).show();
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Handle file upload failure
                        progressBar.setVisibility(View.GONE);
                    });
        }
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA
        }, PERMISSION_REQUEST_CODE);
    }
}