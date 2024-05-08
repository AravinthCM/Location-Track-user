package com.example.locationtrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RetrievePdfActivity extends AppCompatActivity {

    private ListView lvPdfFiles;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private List<String> pdfFileNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_pdf);

        lvPdfFiles = findViewById(R.id.lvPdfFiles);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("uploads");
        pdfFileNames = new ArrayList<>();

        // Retrieve PDF file names from Firebase Storage
        storageReference.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference item : listResult.getItems()) {
                            String fileName = item.getName();
                            pdfFileNames.add(fileName);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(RetrievePdfActivity.this, android.R.layout.simple_list_item_1, pdfFileNames);
                        lvPdfFiles.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RetrievePdfActivity.this, "Failed to retrieve PDF files", Toast.LENGTH_SHORT).show();
                    }
                });

        lvPdfFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedPdfFileName = pdfFileNames.get(position);
                openPdfFile(selectedPdfFileName);
            }
        });
    }

    private void openPdfFile(String pdfFileName) {
        StorageReference fileRef = storageReference.child(pdfFileName);

        try {
            File localFile = File.createTempFile("tempfile", ".pdf");
            fileRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(RetrievePdfActivity.this, "PDF downloaded successfully", Toast.LENGTH_SHORT).show();
                            openPdfViewer(localFile);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RetrievePdfActivity.this, "Failed to download PDF", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openPdfViewer(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", file);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

}