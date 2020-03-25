package com.android.jobber.Ui.fragments.ProfileFragment;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.SqlHelper.myDbAdapter;

public class CustomeDialogSettings {




    Activity activity;
    private EditText editTextUserName,editTextUserPhone,editTextUserAddress,editTextPassword,editTextConfirmPassword;
    private RadioGroup radioGroup;
    private RadioButton radioMale,radioFemale;
    private TextView btnCancel,btnUpdate,btnPassVisible;
    int productType = 0;
    private LinearLayout lin;
    private boolean check = true;
    public void showDialog(final Activity activity, final PresenterProfile presenter, final myDbAdapter mDbAdapter){
        this.activity = activity;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);


         dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.custome_dialoge);
        dialog.show();

        editTextUserAddress = dialog.findViewById(R.id.txt_address);
        editTextUserName = dialog.findViewById(R.id.txt_name);
        editTextUserPhone = dialog.findViewById(R.id.txt_number);
        editTextPassword = dialog.findViewById(R.id.txt_Password);
        editTextConfirmPassword = dialog.findViewById(R.id.txt_confirm_pass);
        btnPassVisible = dialog.findViewById(R.id.change_password);
        lin = dialog.findViewById(R.id.len_password);
        btnCancel = dialog.findViewById(R.id.close);
        btnUpdate = dialog.findViewById(R.id.update);
        radioFemale = dialog.findViewById(R.id.radio_female);
        radioMale = dialog.findViewById(R.id.radio_male);
        radioGroup = dialog.findViewById(R.id.radio_group);
        editTextUserPhone.setText(mDbAdapter.getEmployeeName("phone"));
        editTextUserName.setText(mDbAdapter.getEmployeeName("name"));
        editTextUserAddress.setText(mDbAdapter.getEmployeeName("address"));

       if(mDbAdapter.getEmployeeName("gender").equals("1")){
           radioMale.setChecked(true);
           radioFemale.setChecked(false);
           productType = 1;
       } else{
           radioMale.setChecked(false);
           radioFemale.setChecked(true);
           productType = 2;
       }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productType == 0){

                }else if(TextUtils.isEmpty(editTextUserAddress.getText().toString())||TextUtils.isEmpty(editTextUserName.getText().toString())||TextUtils.isEmpty(editTextUserPhone.getText().toString())) {
                    Message.message(activity,"please fill all the fields");
                }else{
                    if(TextUtils.isEmpty(editTextPassword.getText().toString())){
                        presenter.performUpdateInformation(mDbAdapter.getEmployeeName("user_id"),
                                editTextUserName.getText().toString(),
                                editTextUserAddress.getText().toString(),
                                String.valueOf(productType),
                                editTextUserPhone.getText().toString(),
                                mDbAdapter.getEmployeeName("email"),mDbAdapter.getEmployeeName("image"),mDbAdapter.getEmployeeName("password"));
                    }else{
                        if(editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())){
                            presenter.performUpdateInformation(mDbAdapter.getEmployeeName("user_id"),
                                    editTextUserName.getText().toString(),
                                    editTextUserAddress.getText().toString(),
                                    String.valueOf(productType),
                                    editTextUserPhone.getText().toString(),
                                    mDbAdapter.getEmployeeName("email"),mDbAdapter.getEmployeeName("image"),editTextPassword.getText().toString());
                        }else{
                            Message.message(activity,"Passwords not mismatch");

                        }
                    }


                }

            }
        });



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                switch (index) {
                    case 0:
                        productType = 1;
                        break;
                    case 1:
                        productType = 2;
                        break;
                }
            }
        });

        btnPassVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check){
                    lin.setVisibility(View.VISIBLE);
                    check = false;
                }else{
                    lin.setVisibility(View.GONE);
                    check = true;
                }

            }
        });


    }

}

