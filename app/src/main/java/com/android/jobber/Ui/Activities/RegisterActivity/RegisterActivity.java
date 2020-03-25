package com.android.jobber.Ui.Activities.RegisterActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.jobber.Ui.Activities.Login.LoginActivity;
import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.base.BaseActivity;

public class RegisterActivity extends BaseActivity implements RegisterContract.View{
    ProgressDialog progressDialog;
    private PresenterRegister presenter;
    private EditText editTExtusername,editTextEmail,editTextPassword,editTextConfirmPassword,editTextPhoneNumber;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigster);

        initializeViews();
        setListeners();

    }

    public void loginLink(View view){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
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

        Message.message(RegisterActivity.this, "pleas fill all the flied");
    }

    @Override
    public void loginSuccess() {

        Message.message(RegisterActivity.this, "Welcome");
        LoginActivity.startActivity(RegisterActivity.this);

    }

    @Override
    public void loginError() {
        Message.message(RegisterActivity.this, "Something Went wrong.");
    }

    @Override
    public void emailInvalid() {
        Message.message(RegisterActivity.this, "Please this is not Email make sure from your email.");
    }

    @Override
    public void setUserName(String username) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {
        editTExtusername = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        btnRegister = findViewById(R.id.btn_register);
        progressDialog = new ProgressDialog(this);
        presenter = new PresenterRegister(this,this);
    }


    @Override
    protected void setListeners() {
        btnRegister.setOnClickListener(btnRegisterListener);
    }
    private View.OnClickListener btnRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.performRegister(editTExtusername.getText().toString(),
                    editTextEmail.getText().toString(),
                    editTextPassword.getText().toString(),
                    editTextConfirmPassword.getText().toString(),
                    "0",editTextPhoneNumber.getText().toString());
        }
    };
}
