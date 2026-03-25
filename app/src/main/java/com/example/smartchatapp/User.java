package com.example.smartchatapp;

public class User {
    public String uid;
    public String name;
    public String email;

    public User() {
        // Required for Firebase
    }

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }
}
