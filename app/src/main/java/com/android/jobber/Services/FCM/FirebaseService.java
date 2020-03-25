package com.android.jobber.Services.FCM;

import android.app.ActivityManager;
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

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.jobber.Ui.Activities.MainActivity.MainActivity;
import com.android.jobber.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

public class FirebaseService extends FirebaseMessagingService
{
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");
        String shehab = remoteMessage.getData().get("shehab");
        generateNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));

        if(remoteMessage.getData().size()>0)
        {

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
                if(processInfo.importance== ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
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



        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.KITKAT_WATCH)
        {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcess = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo:runningAppProcess)
            {
                if(processInfo.importance== ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
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



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void generateNotification(String title, String message) {
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.raw.notification_sound);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.new_logo)  //a resource for your custom small icon
                .setContentTitle(title) //the "title" value you sent in your notification
                .setContentText(message) //ditto
                .setAutoCancel(true)  //dismisses the notification on click
                .setSound(alarmSound)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message));


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


            assert mNotificationManager != null;
            notificationBuilder.setChannelId("3");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(3, notificationBuilder.build());
    }

}
