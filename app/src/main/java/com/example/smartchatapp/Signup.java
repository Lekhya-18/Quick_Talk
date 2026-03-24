package com.example.smartchatapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
public class Signup extends AppCompatActivity {
    EditText name, email, password;
    Button signupBtn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signupBtn = findViewById(R.id.signupBtn);
        mAuth = FirebaseAuth.getInstance();
        signupBtn.setOnClickListener(v -> {
            String userName = name.getText().toString();
            String userEmail = email.getText().toString();
            String userPass = password.getText().toString();
            if(userName.isEmpty() || userEmail.isEmpty() || userPass.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, LoginActivity.class));
                        } else {
                            Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}