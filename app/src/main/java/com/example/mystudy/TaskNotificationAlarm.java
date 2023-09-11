package com.example.mystudy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TaskNotificationAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String text =  bundle.getString("event");
        String date = bundle.getString("date") + "" + bundle.getString("time");


        Intent intent1 = new Intent(context, AlertDetails.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.putExtra("message", text);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_IMMUTABLE);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify_001");

                RemoteViews contentView =  new RemoteViews(context.getPackageName(), R.layout.activity_notification);
                PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(context.getApplicationContext(),  0, intent,  PendingIntent.FLAG_IMMUTABLE);
                contentView.setOnClickPendingIntent(R.id.flashButton, pendingSwitchIntent);
                contentView.setTextViewText(R.id.message, text);
                contentView.setTextViewText(R.id.date, date);
                builder.setSmallIcon(R.drawable.ic_baseline_calendar);
                builder.setAutoCancel(true);
                builder.setOngoing(true);
                builder.setAutoCancel(true);
                builder.setPriority(Notification.PRIORITY_HIGH);
                builder.setOnlyAlertOnce(true);
                builder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
                builder.setContent(contentView);
                builder.setContentIntent(pendingIntent);


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    String channelId =  "channel_id";
                    NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
                    channel.enableVibration(true);
                    notificationManager.createNotificationChannel(channel);
                    builder.setChannelId(channelId);
        }
                Notification notification = builder.build();
                notificationManager.notify(1, notification);
    }
}
