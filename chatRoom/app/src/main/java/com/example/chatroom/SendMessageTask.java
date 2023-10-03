package com.example.chatroom;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendMessageTask extends AsyncTask<String, Void, Void> {

    private String messageText;
    private String sender;

    public SendMessageTask(String messageText, String sender) {
        this.messageText = messageText;
        this.sender = sender;
    }

    @Override
    protected Void doInBackground(String... params) {
        String messageText = params[0];

        try {
            URL url = new URL("https://chatroom-efe55-default-rtdb.firebaseio.com/messages.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // 构建 JSON 数据
            String jsonInputString = "{\"message\": \"" + messageText + "\", \"sender\": \"" + sender + "\", \"receiver\": \"User456\", \"timestamp\": " + System.currentTimeMillis() + "}";

            // 向服务器发送数据
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 消息成功发送到 Firebase Realtime Database
            } else {
                // 处理错误
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
