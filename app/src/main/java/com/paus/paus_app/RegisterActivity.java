package com.paus.paus_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.paus.paus_app.common.HelperStuffs.Message;
import com.paus.paus_app.Login.LoginActivity;
import com.paus.paus_app.common.network.WebService;
import com.paus.paus_app.common.model.MainResponse;
import com.paus.paus_app.common.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigster);

        progressDialog = new ProgressDialog(this);

    }

    public void loginLink(View view){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void Registration_func(String strusername,  String stremail, String strpassword, String strconfirm )
    {

        progressDialog.setTitle("wait minuet..");//title which will show  on the dialog box
        progressDialog.setMessage("login now...");//message which will show  on the dialog box
        progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        progressDialog.show();// turn on the dialog box
        if (TextUtils.isEmpty(strusername)||
                TextUtils.isEmpty(stremail)||
                TextUtils.isEmpty(strpassword)||
                TextUtils.isEmpty(strconfirm))
        {
            Message.message(RegisterActivity.this, "pleas fill all the flied");
        }else if (!strpassword.equals(strconfirm))
        {
            Message.message(RegisterActivity.this,"password don't match");
        }else
        {
            User user = new User();
            user.username = strusername;
            user.email = stremail;
            user.password = strpassword;

            WebService.getInstance().getApi().registerUser(user).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    if(response.body().status == 2)
                    {
                        progressDialog.dismiss();
                        Message.message(RegisterActivity.this,response.body().message);

                    }else if(response.body().status==1)
                    {
                        progressDialog.dismiss();// exit from the dialog box because the process is done
                        Message.message(RegisterActivity.this,response.body().message);
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Message.message(RegisterActivity.this,t.getLocalizedMessage());
                }
            });
        }
    }


}
