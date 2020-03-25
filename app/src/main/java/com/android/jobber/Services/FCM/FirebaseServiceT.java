package com.android.jobber.Services.FCM;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.jobber.R;
import com.android.jobber.Ui.Activities.ChatActivity.Chat_activity;
import com.android.jobber.Ui.Activities.MainActivity.MainActivity;
import com.android.jobber.common.SqlHelper.myDbAdapter;
import com.android.jobber.common.model.ChatModel.Messages;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import static android.content.ContentValues.TAG;

public class FirebaseServiceT extends FirebaseMessagingService
{
    public static int NOTIFICATION_ID = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getData().size()>0)
        {
            if (TextUtils.isEmpty(remoteMessage.getData().get("room_id"))){
                generateNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
            }else{
                String messageContent = remoteMessage.getData().get("content");
                String roomId = remoteMessage.getData().get("room_id");
                String userId = remoteMessage.getData().get("user_id");
                String user_name =remoteMessage.getData().get("username");
                String messageType = remoteMessage.getData().get("type");
                String timestamp = remoteMessage.getData().get("timestamp");

                Messages messages = new Messages();
                messages.setContent(messageContent);
                messages.setRoom_id(roomId);
                messages.setUser_id(userId);
                messages.setUser_name(user_name);
                messages.setType(messageType);
                messages.setTimestamp(timestamp);

                myDbAdapter helper = new myDbAdapter(getApplicationContext());
                String  id = helper.getEmployeeName("user_id");


                if(!(Integer.valueOf(user_name)==Integer.parseInt(id))) {
                    if (isAppIsInBackground(this)) {
                        generateNotification(messages.getContent(), "shehab app",messages);

                    } else {
                        Intent intent = new Intent("UpdateChatActivity");
                        intent.putExtra("msg", messages);
                        sendBroadcast(intent);
                    }
                }
            }
            }

       // Log.e("message Received from", remoteMessage.getFrom());
      //  Log.e("message Received from", remoteMessage.getData().get("message"));
    }

    private boolean isAppIsInBackground(Context context)
    {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcess = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo:runningAppProcess)
            {
                if(processInfo.importance==ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                {
                    for (String activeProcess:processInfo.pkgList)
                    {
                        if(activeProcess.equals(context.getPackageName()))
                        {
                            isInBackground=false;

                        }
                    }
                }
            }        }



        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT_WATCH)
        {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcess = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo:runningAppProcess)
            {
                if(processInfo.importance==ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                {
                    for (String activeProcess:processInfo.pkgList)
                    {
                        if(activeProcess.equals(context.getPackageName()))
                        {
                            isInBackground=false;

                        }
                    }
                }
            }
        }else
        {
            List<ActivityManager.RunningTaskInfo> taskInfos = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfos.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName()));
            {
                isInBackground=false;
            }
        }
        return isInBackground;
    }

    private void sendNotfication(Messages messages)
    {
        Intent intent = new Intent(this, Chat_activity.class);
        intent.putExtra("msg",messages);
        intent.putExtra("room_id",Integer.parseInt(messages.getRoom_id()));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this,0, new Intent[]{intent},PendingIntent.FLAG_ONE_SHOT);

       Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       /* NotificationCompat.Builder notifictition = new NotificationCompat.Builder(this,"shehab osama")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("shehabko")
                .setContentText(messages.getContent())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notifictition.build());
        startForeground(1, notifictition.build());

        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Revel Is Running")
                .setTicker("Revel Is Running")
                .setContentText("Click to stop")
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true).build();
        startForeground(1,
                notification);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
        startForeground(1, notification);*/

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void generateNotification(String message, String title, Messages messages) {

        Intent intent = new Intent(getApplicationContext(), Chat_activity.class);
        intent.putExtra("msg",messages);
        intent.putExtra("room_id",Integer.parseInt(messages.getRoom_id()));
        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.general_logo)  //a resource for your custom small icon
                .setContentTitle(title) //the "title" value you sent in your notification
                .setContentText(message) //ditto
                .setAutoCancel(true)  //dismisses the notification on click
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        //Setting up Notification channels for android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.raw.notification_sound);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("3", "CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI,attributes);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            notificationBuilder.setChannelId("3");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        if (NOTIFICATION_ID > 1073741824) {
            NOTIFICATION_ID = 0;
        }

        PendingIntent contentIntent = PendingIntent.getActivity(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID++, notificationBuilder.build());
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void generateNotification(String title, String message) {

        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.raw.notification_sound);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.general_logo)//a resource for your custom small icon
                .setContentTitle(title) //the "title" value you sent in your notification
                .setContentText(message) //ditto
                .setAutoCancel(true)  //dismisses the notification on click
                .setSound(alarmSound)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message));





        // Because the ID remains unchanged, the existing notification is
        // updated.


        //Setting up Notification channels for android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel("3", "CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(alarmSound,attributes);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            if (NOTIFICATION_ID > 1073741824) {
                NOTIFICATION_ID = 0;
            }
            assert mNotificationManager != null;
            notificationBuilder.setChannelId("3");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID++, notificationBuilder.build());
        Log.e(TAG, "generateNotification: "+String.valueOf(NOTIFICATION_ID));
    }
}
