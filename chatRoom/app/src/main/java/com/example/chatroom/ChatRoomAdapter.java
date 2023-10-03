package com.example.chatroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>{

    private static final int VIEW_TYPE_TYPE1 = 1;
    private static final int VIEW_TYPE_TYPE2 = 2;
    private List<Message> messageList = new ArrayList<>();
    private TextView textView;
    private GoogleSignInAccount account;
    @Override
    public int getItemViewType(int position) {
        // 根據位置決定 Item 類型
        if (messageList.get(position).getSender().equals(account.getEmail())) {
            return VIEW_TYPE_TYPE2;
        } else {
            return VIEW_TYPE_TYPE1;
        }
    }

    public ChatRoomAdapter() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }

    }

    @NonNull
    @Override
    public ChatRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
        View view;
        switch (viewType) {
            case VIEW_TYPE_TYPE1:
                view = inflater.inflate(R.layout.item1, parent, false);
                break;
            case VIEW_TYPE_TYPE2:
                view = inflater.inflate(R.layout.item2, parent, false);
                break;
            default:
                throw new IllegalArgumentException("Invalid view type");
        }

        return new ChatRoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomAdapter.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_TYPE1:
                textView = holder.itemView.findViewById(R.id.textViewType1);
                textView.setText(messageList.get(position).getMessage());
                break;
            case VIEW_TYPE_TYPE2:
                textView = holder.itemView.findViewById(R.id.textViewType2);
                textView.setText(messageList.get(position).getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return (messageList == null || messageList.isEmpty()) ? 0 : messageList.size();
    }


    public void setMessageList(List<Message> messageList){
        this.messageList = messageList;
    }

    public void setAccount(GoogleSignInAccount account){
        this.account = account;
    }

}