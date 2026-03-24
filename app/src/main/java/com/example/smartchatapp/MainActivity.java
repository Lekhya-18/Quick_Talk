package com.example.smartchatapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button logoutBtn;
    TextView userEmail;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 🔹 Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        // 🔹 Connect UI
        logoutBtn = findViewById(R.id.logoutBtn);
        userEmail = findViewById(R.id.userEmail);

        // 🔹 Show logged-in user email
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userEmail.setText("Logged in as: " + user.getEmail());
        }
        logoutBtn.setOnClickListener(v -> {
            mAuth.signOut(); // logout
            startActivity(new Intent(MainActivity.this, LoginPg.class));
            finish(); // close current activity
        });
    }
}
