package com.example.smartchatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MSG_TYPE_SENT = 1;
    private static final int MSG_TYPE_RECEIVED = 2;

    private Context context;
    private List<Message> messageList;

    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_message_sent, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_message_received, parent, false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (holder.getItemViewType() == MSG_TYPE_SENT) {
            ((SentViewHolder) holder).sentMsg.setText(message.message);
        } else {
            ((ReceivedViewHolder) holder).receivedMsg.setText(message.message);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).senderId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return MSG_TYPE_SENT;
        } else {
            return MSG_TYPE_RECEIVED;
        }
    }

    public static class SentViewHolder extends RecyclerView.ViewHolder {
        TextView sentMsg;

        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            sentMsg = itemView.findViewById(R.id.sentMessageText);
        }
    }

    public static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView receivedMsg;

        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            receivedMsg = itemView.findViewById(R.id.receivedMessageText);
        }
    }
}
