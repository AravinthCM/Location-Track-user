package com.example.locationtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServiceRequestActivity extends AppCompatActivity {
    TextInputLayout name, type, content;
    Button submit;
    ImageView previous;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_request);

        name = findViewById(R.id.fullname);
        type = findViewById(R.id.type);
        content = findViewById(R.id.content);
        submit = findViewById(R.id.reqsub);
        previous = findViewById(R.id.previous);


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ServiceRequestActivity.this, UserHomeActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subform();
            }
        });
    }

    public void subform() {
        String getName = name.getEditText().getText().toString().trim();
        String getType = type.getEditText().getText().toString().trim();
        String getContent = content.getEditText().getText().toString().trim();

        if (!validateName() || !validateType() || !validateContent()) {
            return;
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            reference = database.getReference("Service requests");

            String service = reference.push().getKey();
            GetServiceReq serviceReq = new GetServiceReq(getName, getType, getContent);
            reference.child(service).setValue(serviceReq);
            Toast.makeText(ServiceRequestActivity.this, "Form successfully Submitted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ServiceRequestActivity.this, UserHomeActivity.class);
            startActivity(intent);
        }
    }

    public Boolean validateName() {
        String val = name.getEditText().getText().toString();

        if (val.isEmpty()) {
            name.setError("Name cannot be Empty");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }

    public Boolean validateType() {
        String val = type.getEditText().getText().toString();

        if (val.isEmpty()) {
            type.setError("Type cannot be Empty");
            return false;
        } else {
            type.setError(null);
            return true;
        }
    }

    public Boolean validateContent() {
        String val = content.getEditText().getText().toString();

        if (val.isEmpty()) {
            content.setError("Content cannot be Empty");
            return false;
        } else {
            content.setError(null);
            return true;
        }
    }
}