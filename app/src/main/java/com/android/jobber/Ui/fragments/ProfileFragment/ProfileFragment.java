package com.android.jobber.Ui.fragments.ProfileFragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.HelperStuffs.PermissionHandlerFragment;
import com.android.jobber.common.HelperStuffs.PermissionsListener;
import com.android.jobber.common.SqlHelper.myDbAdapter;
import com.android.jobber.common.network.Urls;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.FileNotFoundException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends PermissionHandlerFragment implements ProfileContract.View,
        ProfileContract.Model.onFinishedListener,
        PermissionsListener {

    private CircleImageView profileImage;
    private ImageView verifyImage;
    private TextView textViewName,textViewPhone,textViewGender,textViewEmail,textViewAddress,textViewSettings,verificationMark , personalTextView;
    private myDbAdapter myDbAdapter;
    private Uri orgUri;
    ProgressDialog progressDialog;
    PresenterProfile presenter;
    CustomeDialogRequstVerificationMark customeDialogRequstVerificationMark;

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
        checkPermissionAndRequest();
        customeDialogRequstVerificationMark = new CustomeDialogRequstVerificationMark();
        progressDialog = new ProgressDialog(getActivity());
        textViewGender =v.findViewById(R.id.gender_type);
        textViewPhone = v.findViewById(R.id.phone);
        profileImage = v.findViewById(R.id.profile);
        textViewEmail = v.findViewById(R.id.email);
        textViewName = v.findViewById(R.id.username);
        textViewAddress = v.findViewById(R.id.address);
        textViewSettings = v.findViewById(R.id.info);
        verifyImage = v.findViewById(R.id.verify);
        personalTextView = v.findViewById(R.id.personal);
        verificationMark = v.findViewById(R.id.request_verification_mark);
        myDbAdapter = new myDbAdapter(getActivity());
        presenter = new PresenterProfile(this,this,myDbAdapter);
        setupUserData();
    }

    public void setupUserData(){
        textViewAddress.setText(myDbAdapter.getEmployeeName("address"));
        textViewEmail.setText(myDbAdapter.getEmployeeName("email"));
        textViewName.setText(myDbAdapter.getEmployeeName("name"));
        textViewPhone.setText("0"+myDbAdapter.getEmployeeName("phone"));
        if(myDbAdapter.getEmployeeName("verification_code").equals("1")){
            verifyImage.setVisibility(View.VISIBLE);
        }else{
            verifyImage.setVisibility(View.GONE);
        }
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
    public void checkPermissionAndRequest(){
        checkPermissions(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO);
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
        //System.out.println("onActivityResult Main Activity"+data.getData());
        Log.e(TAG, "onActivityResult: helllllllllllo" );
        if(data.getData()!=null){
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {

                    orgUri = result.getUri();
                    if (orgUri != null)
                    {
                        profileImage.setImageURI(orgUri);
                        //    runCloudTextRecognition();
                        presenter.performUpdateProfilePhoto(orgUri, AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),getActivity()
                                ,myDbAdapter.getEmployeeName("phone"),myDbAdapter.getEmployeeName("email"),myDbAdapter.getEmployeeName("gender"),myDbAdapter.getEmployeeName("name")
                                ,myDbAdapter.getEmployeeName("address"));
                    }
                }
            }else{
                System.out.println("onActivityResult Main Activity"+data.getData());
                new CustomeDialogRequstVerificationMark().onActivityResult(requestCode, resultCode, data);
            }
        }else{
            Message.message(getActivity() ,"Something went wrong");
        }

    }

    @Override
    protected void setListeners() {
        textViewSettings.setOnClickListener(textViewSettingsListener);
        profileImage.setOnClickListener(profileImageListener);
        verificationMark.setOnClickListener(verificationMarkListener);
        personalTextView.setOnClickListener(textViewSettingsListener);
    }
    private View.OnClickListener verificationMarkListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            customeDialogRequstVerificationMark.showDialog(getActivity(),presenter);
        }
    };
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
        Message.message(getActivity(),result);
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
    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }

    private void runCloudTextRecognition() {
       // mCloudButton.setEnabled(false);
        FirebaseVisionImage image = null;
        try {
            image = FirebaseVisionImage.fromBitmap(decodeBitmapUri(getActivity(),orgUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FirebaseVisionDocumentTextRecognizer recognizer = FirebaseVision.getInstance()
                .getCloudDocumentTextRecognizer();
        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionDocumentText>() {
                            @Override
                            public void onSuccess(FirebaseVisionDocumentText texts) {
                              //  mCloudButton.setEnabled(true);
                                processCloudTextRecognitionResult(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                              //  mCloudButton.setEnabled(true);
                                e.printStackTrace();
                            }
                        });
    }

    private void processCloudTextRecognitionResult(FirebaseVisionDocumentText text) {
        // Task completed successfully
        if (text == null) {
        //    showToast("No text found");
            return;
        }
       // mGraphicOverlay.clear();
        List<FirebaseVisionDocumentText.Block> blocks = text.getBlocks();
        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionDocumentText.Paragraph> paragraphs = blocks.get(i).getParagraphs();
            for (int j = 0; j < paragraphs.size(); j++) {
                List<FirebaseVisionDocumentText.Word> words = paragraphs.get(j).getWords();
                for (int l = 0; l < words.size(); l++) {

                    Log.e(TAG, "processCloudTextRecognitionResult: "+words.get(1) );
              //      CloudTextGraphic cloudDocumentTextGraphic = new CloudTextGraphic(mGraphicOverlay,
                            //words.get(l));
                   // mGraphicOverlay.add(cloudDocumentTextGraphic);
                }
            }
        }
    }
    @Override
    public void onPermissionGranted(String[] permissions) {

    }

    @Override
    public void onPermissionDenied(String[] permissions) {

    }
}