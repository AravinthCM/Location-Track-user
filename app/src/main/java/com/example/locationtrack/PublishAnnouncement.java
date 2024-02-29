package com.example.locationtrack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PublishAnnouncement extends AppCompatActivity {

    TextInputLayout announce;
    Button annBtn;

    // Firebase
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_announcement);

        announce = findViewById(R.id.announce);
        annBtn = findViewById(R.id.annBtn);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Announcement");

        annBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String announcementText = announce.getEditText().getText().toString();
                pushAnnouncement(announcementText);
            }
        });
    }

    private void pushAnnouncement(String announcementText) {
        if (!announcementText.trim().isEmpty()) {
            AnnounceModel announceModel = new AnnounceModel(announcementText);
            databaseReference.push().setValue(announceModel);
            announce.getEditText().setText("");
        } else {
            // Handle the case when the announcement text is empty
            // You can show an error message or take appropriate action
        }
    }
}
