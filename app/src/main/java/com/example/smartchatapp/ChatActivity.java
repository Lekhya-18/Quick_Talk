package com.example.smartchatapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageEditText;
    private ImageButton sendBtn;
    private Toolbar chatToolbar;

    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    private String receiverId;
    private String senderId;
    private String receiverName;

    private DatabaseReference chatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        receiverId = getIntent().getStringExtra("receiverId");
        receiverName = getIntent().getStringExtra("receiverName");
        senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        chatRef = FirebaseDatabase.getInstance().getReference("Chats");

        chatToolbar = findViewById(R.id.chatToolbar);
        setSupportActionBar(chatToolbar);
        getSupportActionBar().setTitle(receiverName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageEditText = findViewById(R.id.messageEditText);
        sendBtn = findViewById(R.id.sendBtn);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(messageAdapter);

        readMessages();

        sendBtn.setOnClickListener(v -> {
            String msg = messageEditText.getText().toString();
            if (!msg.isEmpty()) {
                sendMessage(senderId, receiverId, msg);
                messageEditText.setText("");
            } else {
                Toast.makeText(this, "Cannot send empty message", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage(String sender, String receiver, String messageText) {
        long timestamp = System.currentTimeMillis();
        Message message = new Message(sender, receiver, messageText, timestamp);
        chatRef.push().setValue(message);
    }

    private void readMessages() {
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    if (message != null && 
                        ((message.senderId.equals(senderId) && message.receiverId.equals(receiverId)) || 
                         (message.senderId.equals(receiverId) && message.receiverId.equals(senderId)))) {
                        messageList.add(message);
                    }
                }
                messageAdapter.notifyDataSetChanged();
                chatRecyclerView.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
