package com.android.jobber.Ui.fragments.ProfileFragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.Message;
import static com.android.jobber.common.HelperStuffs.Constants.BundleKeys.M_URI;
import static android.content.ContentValues.TAG;
public class CustomeDialogRequstVerificationMark extends DialogFragment {


    public static final int PICK_FROM_GALLERY =165 ;
    Activity activity;
    private EditText editTExtReason;

    TextView btnClose,btnSendRequest;
    private ImageView uploadImage;
    private  Uri uri;
    public void showDialog(final Activity activity , final PresenterProfile presenterProfile){
        this.activity = activity;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);


         dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.custome_dialoge_request_verfication);
        dialog.show();

        uploadImage = dialog.findViewById(R.id.idImage);
        editTExtReason = dialog.findViewById(R.id.reasonId);

        btnClose = dialog.findViewById(R.id.close);
        btnSendRequest = dialog.findViewById(R.id.btn_send_request);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(editTExtReason.getText().toString())) {
                    Message.message(activity,"please fill all the fields");
                }else{

                   try {
                       Log.d(TAG, "onClick: "+ M_URI.toString());
                       presenterProfile.performSendRequestVerificationMark(M_URI,activity,editTExtReason.getText().toString());
                   }catch (NullPointerException ex){
                       System.err.println(ex.getLocalizedMessage());
                   }

                    }
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e(TAG, "onActivityResult: "+"tests" );
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && data !=null) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getData();
                M_URI = data.getData();
                Log.e(TAG, "onActivityResultttt: "+uri );
            }
        }else{

            if (resultCode == Activity.RESULT_OK) {
              //  uri = data.getData();
                Log.e(TAG, "onActivityResult: "+uri );

            }
        }
    }


    public void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
        //startActivityForResult(intent,111);
    }
}

