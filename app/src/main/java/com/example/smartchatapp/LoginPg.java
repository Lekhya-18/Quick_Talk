package com.example.smartchatapp;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
public class LoginPg extends AppCompatActivity {

        EditText email, password;
        Button loginBtn;
        TextView signupText;
        FirebaseAuth mAuth;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_pg);
            email = findViewById(R.id.email);
            password = findViewById(R.id.password);
            loginBtn = findViewById(R.id.loginBtn);
            signupText = findViewById(R.id.signupText);
            mAuth = FirebaseAuth.getInstance();
            loginBtn.setOnClickListener(v -> {
                String userEmail = email.getText().toString();
                String userPass = password.getText().toString();
                if(userEmail.isEmpty() || userPass.isEmpty()){
                    Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(userEmail, userPass)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this, SplashActivity.class));
                            } else {
                                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            });
            signupText.setOnClickListener(v -> {
                startActivity(new Intent(this, Signup.class));
            });
        }
    }