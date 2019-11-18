package com.paus.paus_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paus.paus_app.HelperStuffs.Message;
import com.paus.paus_app.Login.LoginActivity;
import com.paus.paus_app.WebServiceapi.WebService;
import com.paus.paus_app.model.MainResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {

    EditText editTextPassword,editTextConfirmPassword;
    Button doneButtonEdit;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        email = getIntent().getExtras().getString("email").toString();
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        doneButtonEdit = findViewById(R.id.doneEditPassword);

        doneButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = editTextPassword.getText().toString();
                String confirm_password = editTextConfirmPassword.getText().toString();

                ResetPasswordFunction(password,confirm_password,email);
            }
        });
    }

    public void ResetPasswordFunction(String Password,String ConfirmPassword,String email){
        if(TextUtils.isEmpty(Password)||TextUtils.isEmpty(ConfirmPassword)) {
          Message.message(ResetPassword.this,"Fill in all fields");
        }else {
            if (!Password.equals(ConfirmPassword)) {
                Message.message(ResetPassword.this, "Mismatch Password");
            } else {

                WebService.getInstance().getApi().updatPassword(email,Password).enqueue(new Callback<MainResponse>() {
                    @Override
                    public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                        Message.message(ResetPassword.this,response.body().message);
                        if(response.isSuccessful()&&response.body().status==1){
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    }

                    @Override
                    public void onFailure(Call<MainResponse> call, Throwable t) {

                    }
                });
            }
        }
    }
}
