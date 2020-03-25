package com.android.jobber.Ui.Activities.ForgetPasswordActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.jobber.R;
import com.android.jobber.Ui.Activities.VerficationActivity.VerificationActivity;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.base.BaseActivity;

public class ForgetPassword extends BaseActivity implements ForgetContract.View,ForgetContract.Model.onFinishedListener {

    private Button btnVerification;
    private EditText editTextEmail;
    private ProgressDialog progressDialog;
    private String email;
    private PresenterForgetPassword presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_activity);
        initializeViews();
        setListeners();
    }



    @Override
    public void onFinished(String result) {
        if(result.equals("0")){
            Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
            intent.putExtra("emailKey",email);
            startActivity(intent);
        }else{
            Message.message(ForgetPassword.this,result);
        }
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void showProgress() {
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Sending verification .....");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {
        btnVerification = findViewById(R.id.btn_verification);
        editTextEmail = findViewById(R.id.editTextEmail);
        progressDialog = new ProgressDialog(this);
        presenter = new PresenterForgetPassword(this,this);
    }

    @Override
    protected void setListeners() {
        btnVerification.setOnClickListener(btnVerificationListener);
    }
    private View.OnClickListener btnVerificationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
             email = editTextEmail.getText().toString();
             presenter.performSendVerification(email);
        }
    };
}
