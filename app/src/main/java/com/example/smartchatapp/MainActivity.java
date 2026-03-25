package com.example.smartchatapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button logoutBtn;
    TextView userEmailText;
    RecyclerView userRecyclerView;
    UserAdapter userAdapter;
    List<User> userList;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 🔹 Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // 🔹 Connect UI
        logoutBtn = findViewById(R.id.logoutBtn);
        userEmailText = findViewById(R.id.userEmail);
        userRecyclerView = findViewById(R.id.userRecyclerView);

        // 🔹 Setup RecyclerView
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(this, userList);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.setAdapter(userAdapter);

        // 🔹 Show logged-in user email
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userEmailText.setText("Logged in as: " + currentUser.getEmail());
            fetchUsers(currentUser.getUid());
        } else {
            // If user is not logged in, go to Login screen
            startActivity(new Intent(MainActivity.this, LoginPg.class));
            finish();
        }

        logoutBtn.setOnClickListener(v -> {
            mAuth.signOut(); // logout
            startActivity(new Intent(MainActivity.this, LoginPg.class));
            finish(); // close current activity
        });
    }

    private void fetchUsers(String currentUserId) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    // Add all users except the current logged-in user
                    if (user != null && !dataSnapshot.getKey().equals(currentUserId)) {
                        userList.add(user);
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if needed
            }
        });
    }
}
