package com.codekiller.nownews.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.codekiller.nownews.MainActivity;
import com.codekiller.nownews.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessaging extends FirebaseMessagingService {
    public static final String TAG = "FIREBASE MESSAGING";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived: message - "+remoteMessage.getNotification().getBody());
        sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    private void sendNotification(String title, String body) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.news_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);
        Intent intent;
        if( title.equalsIgnoreCase("update available") ){
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(body));
        }else{
            intent = new Intent(this,MainActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("notify","notification",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            notification.setChannelId("notify");
        }
        notificationManager.notify(0,notification.build());
    }

}
