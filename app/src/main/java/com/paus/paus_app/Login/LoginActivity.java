package com.paus.paus_app.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paus.paus_app.ForgetPassword;
import com.paus.paus_app.common.HelperStuffs.Message;
import com.paus.paus_app.MainActivity;
import com.paus.paus_app.R;
import com.paus.paus_app.RegisterActivity;
import com.paus.paus_app.SqlHelper.myDbAdapter;
public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener {

    Button btnLogin;
    EditText editTextEmail,editTextPassword;
    ProgressDialog progressDialog;
    myDbAdapter helper;
    LoginModel presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UiDeclaration();
    }
    public void UiDeclaration(){
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);
        presenter = new LoginModel(this);
        helper = new myDbAdapter(this);
        btnLogin.setOnClickListener(this);
    }
    public void viewRegisterClicked(View view)
    {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    public void viewForgotPassword(View view){
        startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
    }


    @Override
    public void showProgress() {
        progressDialog.setTitle("wait minuet..");//title which will show  on the dialog box
        progressDialog.setMessage("login now...");//message which will show  on the dialog box
        progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        progressDialog.show();// turn on the dialog box
    }

    @Override
    public void hideProgress() {
    progressDialog.dismiss();
    }

    @Override
    public void loginValidations() {
        Message.message(LoginActivity.this,"pleas fill all the flied");
    }

    @Override
    public void loginSuccess() {
        Message.message(LoginActivity.this,"login successfully");
        hideProgress();
        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void loginError() {
        hideProgress();
        Message.message(LoginActivity.this,"Login Failure");
    }

    @Override
    public void emailInvalid() {
        hideProgress();
        Message.message(LoginActivity.this,"Please Make Sure From Your Email");
    }



    @Override
    public void setUserName(String username) {
        helper.insertData(username,editTextPassword.getText().toString(),editTextEmail.getText().toString());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_login:

                String strEmail = editTextEmail.getText().toString();
                String strPassword = editTextPassword.getText().toString();
                if(TextUtils.isEmpty(strEmail)||TextUtils.isEmpty(strPassword)){
                    Message.message(getApplicationContext(),"Please Enter Email and Password");

                    hideProgress();
                }else {
                    showProgress();
                    presenter.performLogin(strEmail,strPassword);
                }

                break;
            default :
                Message.message(getApplicationContext(),"");
        }
    }
}
