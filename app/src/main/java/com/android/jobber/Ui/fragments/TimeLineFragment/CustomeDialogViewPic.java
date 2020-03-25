package com.android.jobber.Ui.fragments.TimeLineFragment;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import com.android.jobber.R;
import com.android.jobber.common.network.Urls;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class CustomeDialogViewPic {




    Activity activity;
    public void showDialog(final Activity activity, final String imageName){
        this.activity = activity;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);


         dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);

        dialog.setContentView(R.layout.custome_dialoge_view_pic);
        dialog.show();



        PhotoView photoView =dialog.findViewById(R.id.photo_view);

        Picasso.with(activity)
                .load(Urls.IMAGE_URL+imageName)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(photoView);



    }

}
