package com.android.jobber.Ui.fragments.JobberAccountFragment;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;

public class CustomeDialogRate {




    Activity activity;
    private EditText editTextrate;
    private TextView btnUpload,btnClose;
    Dialog dialog;

    public CustomeDialogRate(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
    }

    public void showDialog(final Activity activity, final PresenterJobberAccount presenter, final String rate, final String userAccountId){
        this.activity = activity;
       // dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);


         dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.custome_dialoge_rate);
        dialog.show();

        editTextrate =dialog.findViewById(R.id.txt_rate);
        btnUpload = dialog.findViewById(R.id.btn_rate);
        btnClose = dialog.findViewById(R.id.close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppPreferences.setString(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,activity,"0")+userAccountId,rate,activity);
                presenter.performRateUs(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,activity,"0"),userAccountId,rate,editTextrate.getText().toString());

            }
        });
    }

    public void hideDialog() {
        dialog.dismiss();
    }
}

