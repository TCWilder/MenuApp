package com.example.menuproject;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class SetTime extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context, "notifySetTime")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("Notify")
                .setContentText("We will notify you when your food is ready")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
    }
}
