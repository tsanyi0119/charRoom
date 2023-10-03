package com.example.chatroom;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class Type1ViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewType1; // 這是 Type1 的視圖元素

    public Type1ViewHolder(View itemView) {
        super(itemView);
        textViewType1 = itemView.findViewById(R.id.textViewType1);
        // 在這裡初始化 Type1ViewHolder 的視圖元素，並添加相應的事件處理邏輯
    }
}
