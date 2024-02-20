package com.example.locationtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginAdminActivity extends AppCompatActivity {

    Button adminSUB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_admin);

        adminSUB=findViewById(R.id.adminSUB);

        adminSUB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAdminActivity.this,AdminHome.class);
                startActivity(intent);
            }
        });
    }
}