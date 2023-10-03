package com.example.chatroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class ChatRoomActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ChatRoomAdapter recyclerViewAdapter;
    EditText edit_message;
    ImageView img_sendMessage;
    int itemCount;
    MessageManager messageManager = new MessageManager();
    private Handler handler = new Handler();
    private Runnable runnable;
    List<Message> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        edit_message = findViewById(R.id.edit_message);
        img_sendMessage = findViewById(R.id.img_sendMessage);
        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        GoogleSignInAccount account = intent.getParcelableExtra("account");

        getMessages();
        recyclerViewAdapter = new ChatRoomAdapter();
        recyclerViewAdapter.setAccount(account);
        recyclerViewAdapter.setMessageList(messageList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                // 如果 RecyclerView 的高度變化了（通常是由於軟鍵盤的顯示/隱藏），則滾動到底部
                if (bottom < oldBottom) {
                    itemCount = recyclerView.getAdapter().getItemCount();
                    recyclerView.smoothScrollToPosition(itemCount - 1);
                }
            }
        });
        img_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ChatRoomActivity", "Message：" + edit_message.getText());
                String messageText = edit_message.getText().toString();
                new SendMessageTask(messageText,account.getEmail()).execute(messageText);
                getMessages();
            }
        });
        //訂閱 "news" 的主題，以接收與該主題相關的 FCM 通知。
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        //獲取裝置的 FCM 註冊令牌 (token)
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful())return;
                String token = task.getResult();

            }
        });
        startTimer();
    }

    private void getMessages(){
        messageManager.getMessages()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messages -> {
                            // 成功获取消息列表
                            // 在这里处理消息列表
                            messageList.clear();
                            for(int i = 0 ; i < messages.size() ; i++){
                                messageList.add(messages.get(i));
                            }
                            recyclerViewAdapter.setMessageList(messageList);
                            recyclerViewAdapter.notifyDataSetChanged();
                        },
                        error -> {
                            // 获取消息列表时出错
                            // 处理错误
                            Toast.makeText(this, "获取消息列表出错: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                );
    }

    private void startTimer() {
        runnable = new Runnable() {
            @Override
            public void run() {
                getMessages();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }


}