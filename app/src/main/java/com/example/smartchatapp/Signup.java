package com.example.smartchatapp;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText name, email, password;
    Button signupBtn;
    TextView loginText;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 🔹 UI 연결
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signupBtn = findViewById(R.id.signupBtn);
        loginText = findViewById(R.id.loginText);
        // 🔹 Firebase init
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // 🔹 Signup Button
        signupBtn.setOnClickListener(v -> {

            String userName = name.getText().toString().trim();
            String userEmail = email.getText().toString().trim();
            String userPass = password.getText().toString().trim();

            // ✅ Validation
            if (userName.isEmpty() || userEmail.isEmpty() || userPass.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔥 Create user in Firebase Auth
            mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            String userId = mAuth.getCurrentUser().getUid();

                            // 🔥 Create user object with UID
                            User user = new User(userId, userName, userEmail);

                            // 🔥 Save user in Realtime DB
                            databaseReference.child(userId).setValue(user);

                            Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show();

                            // 🔄 Go to Login Page
                            startActivity(new Intent(Signup.this, LoginPg.class));
                            finish();

                        } else {
                            Toast.makeText(this, "Signup Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
        // 🔹 Go to Login Page
        loginText.setOnClickListener(v -> {
            startActivity(new Intent(Signup.this, LoginPg.class));
        });
    }
}
