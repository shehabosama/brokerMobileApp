package com.paus.paus_app.Dialogs;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.paus.paus_app.R;

public class Custome_dialogs_group {
    TextView btn_close;
    TextView btn_send;
    TextView txt_name,txt_number;


    public void showDialog(final Activity activity, final String name , final String number){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
         dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.custome_dialoge);
        dialog.show();
        btn_close = dialog.findViewById(R.id.close);
        btn_send = dialog.findViewById(R.id.send);
        txt_number = dialog.findViewById(R.id.txt_name);
        txt_name = dialog.findViewById(R.id.txt_number);

        txt_name.setText(  "Name    : "+name);
        txt_number.setText("Number : "+number);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, 20);

                }
                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(number))
                {

                    Toast.makeText(activity, "please select your contact", Toast.LENGTH_LONG).show();
                }else {


                    SmsManager smgr = SmsManager.getDefault();
                    smgr.sendTextMessage(number, null, "hello dear" + name + "", null, null);
                    Toast.makeText(activity, "send message successfully", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }
}
