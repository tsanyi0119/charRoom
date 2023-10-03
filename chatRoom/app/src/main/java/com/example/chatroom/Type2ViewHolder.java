package com.example.chatroom;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class Type2ViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageViewType2; // 這是 Type2 的視圖元素
    public TextView textViewType2; // 這是 Type2 的視圖元素

    public Type2ViewHolder(View itemView) {
        super(itemView);
        textViewType2 = itemView.findViewById(R.id.textViewType2);
        // 在這裡初始化 Type2ViewHolder 的視圖元素，並添加相應的事件處理邏輯
    }
}
