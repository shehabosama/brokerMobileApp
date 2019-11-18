package com.paus.paus_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.paus.paus_app.HelperStuffs.Message;
import com.paus.paus_app.WebServiceapi.WebService;
import com.paus.paus_app.model.verificationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity {
    Button btnVerification;
    EditText editTextVerification;
    String email;
    TextView errorMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
         email = getIntent().getExtras().getString("emailKey").toString();


        btnVerification = findViewById(R.id.btn_verification);
        editTextVerification = findViewById(R.id.editTextVerification);
        errorMessage = findViewById(R.id.errorMessage);

        btnVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = editTextVerification.getText().toString();

                verificationCode(code,email);
            }
        });
    }

    private void verificationCode(String code, final String email) {
        WebService.getInstance().getApi().reciveVerification(email,code).enqueue(new Callback<verificationResponse>() {
            @Override
            public void onResponse(Call<verificationResponse> call, Response<verificationResponse> response) {
                Message.message(VerificationActivity.this,response.body().error);
                if(response.body().error.equals("0")){
                    startActivity(new Intent(getApplicationContext(),ResetPassword.class).putExtra("email",email)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                    errorMessage.setVisibility(View.GONE);
                }else{
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<verificationResponse> call, Throwable t) {
                Message.message(VerificationActivity.this,"check your internet");
            }
        });
    }
}
