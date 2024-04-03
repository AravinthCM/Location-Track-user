package com.example.locationtrack;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private ListView listView;
    private List<UserData> userDataList;
    private FirebaseAuth mAuth;
    private Query mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
    }
}