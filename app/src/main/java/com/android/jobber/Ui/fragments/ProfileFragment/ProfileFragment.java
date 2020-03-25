package com.android.jobber.Ui.fragments.ProfileFragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.SqlHelper.myDbAdapter;
import com.android.jobber.common.base.BaseFragment;
import com.android.jobber.common.network.Urls;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements ProfileContract.View,ProfileContract.Model.onFinishedListener{


    private CircleImageView profileImage;
    private TextView textViewName,textViewPhone,textViewGender,textViewEmail,textViewAddress,textViewSettings;
    private myDbAdapter myDbAdapter;
    private Uri orgUri;
    ProgressDialog progressDialog;
    PresenterProfile presenter;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {

        progressDialog = new ProgressDialog(getActivity());
        textViewGender =v.findViewById(R.id.gender_type);
        textViewPhone = v.findViewById(R.id.phone);
        profileImage = v.findViewById(R.id.profile);
        textViewEmail = v.findViewById(R.id.email);
        textViewName = v.findViewById(R.id.username);
        textViewAddress = v.findViewById(R.id.address);
        textViewSettings = v.findViewById(R.id.info);
        myDbAdapter = new myDbAdapter(getActivity());
        presenter = new PresenterProfile(this,this,myDbAdapter);
        textViewAddress.setText(myDbAdapter.getEmployeeName("address"));
        textViewEmail.setText(myDbAdapter.getEmployeeName("email"));
        textViewName.setText(myDbAdapter.getEmployeeName("name"));
        textViewPhone.setText("0"+myDbAdapter.getEmployeeName("phone"));
        if(myDbAdapter.getEmployeeName("gender").equals("1")){
            textViewGender.setText("male");
        }else if(myDbAdapter.getEmployeeName("gender").equals("2")){
            textViewGender.setText("female");
        }else{
            textViewGender.setText("None");
        }
        Picasso.with(getActivity())
                .load(Urls.IMAGE_URL +myDbAdapter.getEmployeeName("image"))
                .placeholder(R.drawable.backgroundprof)
                .into(profileImage);


    }
    private void CropActivity()// to  open the gallery or camera or any app can take a photo  and select the photo and crop the photo and submit crop
    {
        CropImage.activity()
                // .setGuidelines(CropImageView.Guidelines.ON)
                //  .setAspectRatio(1, 1)
                //.setMaxCropResultSize(700, 700)
                .start(getContext(),this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                orgUri = result.getUri();
                if (orgUri != null)
                {
                    profileImage.setImageURI(orgUri);
                    presenter.performUpdateProfilePhoto(orgUri, AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),getActivity()
                    ,myDbAdapter.getEmployeeName("phone"),myDbAdapter.getEmployeeName("email"),myDbAdapter.getEmployeeName("gender"),myDbAdapter.getEmployeeName("name")
                    ,myDbAdapter.getEmployeeName("address"));
                }

            }else
            {

            }



        }
    }

    @Override
    protected void setListeners() {
        textViewSettings.setOnClickListener(textViewSettingsListener);
        profileImage.setOnClickListener(profileImageListener);
    }
    private View.OnClickListener profileImageListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          CropActivity();
        }
    };
    private View.OnClickListener textViewSettingsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CustomeDialogSettings customeDialogSettings = new CustomeDialogSettings();
            customeDialogSettings.showDialog(getActivity(),presenter,myDbAdapter);
        }
    };

    @Override
    public void onFinished(String result) {
        Message.message(getActivity(),"Update Successfully");
        if(result.equals("Uploaded Profile image successfully")){

        }

    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void showProgress() {
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Please Wait while Updating Your changes");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }
}
