package com.example.chatroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificationTarget extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_target);
        String title = getIntent().getStringExtra("notification_title");
        String body = getIntent().getStringExtra("notification_body");

        TextView tvResult = findViewById(R.id.textView_Result);
        tvResult.setText("收到訊息：\n"+body);

//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(result));
//        startActivity(intent);
//        finish();

    }
}