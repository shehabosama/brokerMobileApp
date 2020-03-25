package com.android.jobber.Ui.Activities.VerficationActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.jobber.R;
import com.android.jobber.Ui.Activities.ResetPasswordActivity.ResetPassword;
import com.android.jobber.common.base.BaseActivity;

public class VerificationActivity extends BaseActivity implements VerificationContract.Model.onFinishedListener,VerificationContract.View {
    private Button btnVerification;
    private EditText editTextVerification;
    private String email;
    private TextView errorMessage;
    PresenterVerfication presenter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        initializeViews();
        setListeners();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {
        email = getIntent().getExtras().getString("emailKey").toString();
        btnVerification = findViewById(R.id.btn_verification);
        editTextVerification = findViewById(R.id.editTextVerification);
        errorMessage = findViewById(R.id.errorMessage);
        progressDialog = new ProgressDialog(this);
        presenter = new PresenterVerfication(this,this);

    }

    @Override
    protected void setListeners() {
        btnVerification.setOnClickListener(btnVerificationListener);
    }

    private View.OnClickListener btnVerificationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String code = editTextVerification.getText().toString();
            presenter.performVerification(code,email);
        }
    };

    @Override
    public void onFinished(String result) {
        if(result.equals("0")){
            startActivity(new Intent(getApplicationContext(), ResetPassword.class).putExtra("email",email)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
            errorMessage.setVisibility(View.GONE);
        }else{
            errorMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailuer(Throwable t) {

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
}
