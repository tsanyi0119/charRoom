package com.example.chatroom;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class FCMService extends FirebaseMessagingService {
    public static final String TAG = FCMService.class.getSimpleName()+"My";
    private String CHANNEL_ID = "Coder";
    public FCMService() {}

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "裝置Token: "+s);
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived: " + remoteMessage.getData());
        //從這邊開始往下
//        /**檢查手機版本是否支援通知；若支援則新增"頻道"*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e(TAG, "onMessageReceived"+"test" );
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "DemoCode", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel);
        }

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        String title = notification.getTitle();
        String body = notification.getBody();
        Intent intent = new Intent(this,NotificationTarget.class);
        //如果 NotificationTarget 活動已經在堆棧中（在同一任務堆棧中），它會被清除，以確保它是當前的頂部活動。
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("notification_title", title);
        intent.putExtra("notification_body", body);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0
                ,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        /**建置通知欄位的內容*/
        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(FCMService.this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_pending_24)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent);

        /**發出通知*/
        NotificationManagerCompat notificationManagerCompat
                = NotificationManagerCompat.from(FCMService.this);
        notificationManagerCompat.notify(1,builder.build());
        //到這邊
    }
}
