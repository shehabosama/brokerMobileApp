package com.android.jobber.Services.FCM;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;

public class FCMTokenRefreshListenerService extends FirebaseMessagingService
{
    public FCMTokenRefreshListenerService() {
    }



    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Intent intent = new Intent(this, FCMRegisterationService.class);
        intent.putExtra("refreshed", true);
        startService(intent);
    }
}
