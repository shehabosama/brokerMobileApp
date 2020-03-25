package com.android.jobber.Ui.Activities.ResetPasswordActivity;

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

public class ResetPassword extends BaseActivity implements ResetPasswordContract.Model.onFinishedListener,ResetPasswordContract.View {

    private EditText editTextPassword,editTextConfirmPassword;
    private Button doneButtonEdit;
    private String email;
    private ProgressDialog progressDialog;
    private PresenterResetPassword presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initializeViews();
        setListeners();
    }



    @Override
    public void onFinished(String result) {
        if(result.equals("1")){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        }else{
            Message.message(ResetPassword.this,"Something Went Wrong...");
        }
    }

    @Override
    public void onFailuer(Throwable t) {

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {
        email = getIntent().getExtras().getString("email").toString();
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        doneButtonEdit = findViewById(R.id.doneEditPassword);
        progressDialog = new ProgressDialog(this);
        presenter = new PresenterResetPassword(this,this);
    }

    @Override
    protected void setListeners() {
        doneButtonEdit.setOnClickListener(doneButtonEditListener);
    }
    private View.OnClickListener doneButtonEditListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String password = editTextPassword.getText().toString();
            String confirm_password = editTextConfirmPassword.getText().toString();
            presenter.performResetPassword(password,confirm_password,email);
        }
    };
}
