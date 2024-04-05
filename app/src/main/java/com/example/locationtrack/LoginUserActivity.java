package com.example.locationtrack;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUserActivity extends AppCompatActivity {

    TextInputLayout fullName,password;
    Button login;
    private FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    TextView forgetpassword;
    TextView already;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);

        already=findViewById(R.id.already);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginUserActivity.this,RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        forgetpassword=findViewById(R.id.forgetpassword);
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginUserActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        Button adminonto = findViewById(R.id.adminonto);
        adminonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginUserActivity.this, LoginAdminActivity.class);
                startActivity(intent);
            }
        });

        progressBar=findViewById(R.id.progress1);

        fullName=findViewById(R.id.fullname);
        password=findViewById(R.id.passs1);
        login=findViewById(R.id.sub2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ValidateFullname()|!ValidatePassword()){
                }else{
                    loginUser();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void loginUser(){
        String userUserName=fullName.getEditText().getText().toString().trim();
        String userPassword=password.getEditText().getText().toString().trim();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(userUserName, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginUserActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginUserActivity.this, UserHomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginUserActivity.this, "Login failed. Please check your email and password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private Boolean ValidateFullname(){
        String val=fullName.getEditText().getText().toString();

        if (val.isEmpty()){
            fullName.setError("UserId cannot be Empty");
            return false;
        }
        else{
            fullName.setError(null);
            return true;
        }
    }
    private Boolean ValidatePassword(){
        String val=password.getEditText().getText().toString();

        if (val.isEmpty()){
            password.setError("Password cannot be Empty");
            return false;
        }
        else{
            password.setError(null);
            return true;
        }
    }

}