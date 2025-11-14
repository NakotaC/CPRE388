package com.example.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private static final int VIEW_USER = 0;
    private static final int VIEW_AI   = 1;

    private final List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    // Return 0 for user, 1 for AI
    @Override
    public int getItemViewType(int position) {
        return messages.get(position).isUser ? VIEW_USER : VIEW_AI;
    }

    // Inflate layout based on view type, but use ONE ViewHolder class
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = (viewType == VIEW_USER)
                ? inf.inflate(R.layout.item_user_message, parent, false)
                : inf.inflate(R.layout.item_ai_message, parent, false);
        return new ViewHolder(v);
    }

    // Bind message text
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.messageText.setText(messages.get(position).text);
    }

    // Size
    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Single, simple ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
        }
    }
}
