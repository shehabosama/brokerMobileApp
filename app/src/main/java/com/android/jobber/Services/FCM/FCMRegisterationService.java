package com.android.jobber.Services.FCM;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class FCMRegisterationService extends IntentService {
    SharedPreferences preferences;

    public FCMRegisterationService() {
        super("FCM");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        // get Default Shard Preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // get token from Firebase
       // String token = FirebaseInstanceId.getInstance().getToken();

//        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//            @Override
//            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//
//                if (task.isSuccessful())
//                {
//                    String token = task.getResult().getToken();
//
//                    //Toast.makeText(FCMRegisterationService.this, token, Toast.LENGTH_SHORT).show();
//
//                    if (intent.getExtras() != null) {
//                        boolean refreshed = intent.getExtras().getBoolean("refreshed");
//                        if (refreshed) preferences.edit().putBoolean("token_sent", false).apply();
//                    }
//
//                    // if token_sent value is false then use method sendTokenToServer to send token to server
//                    if (!preferences.getBoolean("token_sent", false)){
//
//                    }
//
//                }else
//                {
//                    Toast.makeText(FCMRegisterationService.this, ""+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });





        // check if intent is null or not if it isn't null we will ger refreshed value and
        // if its true we will override token_sent value to false and apply


    }


}
