package com.example.locationtrack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText; // Changed from TextInputEditText to EditText
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserActivity extends AppCompatActivity {

    private TextInputLayout emailInputLayout, rollNumberInputLayout, busNumberInputLayout, placeInputLayout, passwordInputLayout;
    private EditText emailEditText, rollNumberEditText, busNumberEditText, placeEditText, passwordEditText; // Changed from TextInputEditText to EditText
    private Button submitButton;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        emailInputLayout = findViewById(R.id.fullname);
        rollNumberInputLayout = findViewById(R.id.Roll);
        busNumberInputLayout = findViewById(R.id.Bus);
        placeInputLayout = findViewById(R.id.Place);
        passwordInputLayout = findViewById(R.id.passs1);

        emailEditText = emailInputLayout.getEditText();
        rollNumberEditText = rollNumberInputLayout.getEditText();
        busNumberEditText = busNumberInputLayout.getEditText();
        placeEditText = placeInputLayout.getEditText();
        passwordEditText = passwordInputLayout.getEditText();

        submitButton = findViewById(R.id.sub2);
        progressBar = findViewById(R.id.progress1);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String rollNumber = rollNumberEditText.getText().toString().trim();
        String busNumber = busNumberEditText.getText().toString().trim();
        String place = placeEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || rollNumber.isEmpty() || busNumber.isEmpty() || place.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                databaseReference.child(userId).setValue(new Student(email, rollNumber, busNumber, place))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterUserActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                    // Navigate to the next activity or finish this one
                                                } else {
                                                    Toast.makeText(RegisterUserActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(RegisterUserActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
