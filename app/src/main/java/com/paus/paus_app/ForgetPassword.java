package com.paus.paus_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paus.paus_app.HelperStuffs.Message;
import com.paus.paus_app.WebServiceapi.WebService;
import com.paus.paus_app.model.verificationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {

    Button btnVerification;
    EditText editTextEmail;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_activity);
        btnVerification = findViewById(R.id.btn_verification);
        editTextEmail = findViewById(R.id.editTextEmail);
        progressDialog = new ProgressDialog(this);
        btnVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();

                sendVerification(email);
            }
        });


    }

    private void sendVerification(final String email) {
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Sending verification .....");
        progressDialog.show();
        WebService.getInstance().getApi().sendVerification(email).enqueue(new Callback<verificationResponse>() {
            @Override
            public void onResponse(Call<verificationResponse> call, Response<verificationResponse> response) {
                Message.message(ForgetPassword.this,"done  "+response.body().error);
                if(response.body().error.equals("0")){
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(),VerificationActivity.class);
                    intent.putExtra("emailKey",email);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<verificationResponse> call, Throwable t) {

            }
        });
    }
}
