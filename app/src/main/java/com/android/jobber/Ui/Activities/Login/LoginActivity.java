package com.android.jobber.Ui.Activities.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.jobber.R;
import com.android.jobber.Ui.Activities.ForgetPasswordActivity.ForgetPassword;
import com.android.jobber.Ui.Activities.MainActivity.MainActivity;
import com.android.jobber.Ui.Activities.RegisterActivity.RegisterActivity;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.SqlHelper.myDbAdapter;
import com.android.jobber.common.model.User;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener ,LoginContract.Model.onFinishedListener{

    Button btnLogin;
    EditText editTextEmail,editTextPassword;
    ProgressDialog progressDialog;
    myDbAdapter helper;
    PresenterLogin presenter;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context, LoginActivity.class));
    }
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
        presenter = new PresenterLogin(this,this,this);
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
       // progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
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
        //Message.message(LoginActivity.this,"login successfully");
        hideProgress();
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

    @Override
    public void onFinished(User user) {
        helper.insertData(user.username,editTextPassword.getText().toString(),editTextEmail.getText().toString(),String.valueOf(user.phoneNo),String.valueOf(user.gender),user.address,user.userImage,String.valueOf(user.id));
    }

    @Override
    public void onFinished(String result) {
        Message.message(LoginActivity.this,result);
        hideProgress();
        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();

    }

    @Override
    public void onFailuer(Throwable t) {

    }
}
