package com.android.jobber.Ui.fragments.PublishingFragment;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.android.jobber.common.HelperStuffs.FileUtil;
import com.android.jobber.common.model.MainResponse;
import com.android.jobber.common.network.WebService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jaiselrahman.filepicker.activity.FilePickerActivity.TAG;

public class PresenterPublish implements PublishingContract.Presenter {

    PublishingContract.Model.onFinishedListener mModel;
    PublishingContract.View mView;

    public PresenterPublish (PublishingContract.Model.onFinishedListener mModel  ,PublishingContract.View mView){
        this.mView = mView;
        this.mModel = mModel;
    }
    @Override
    public void performPublish(List<Uri> uris, Context context,String userId,String flatDescription ,String flatLocation,String flatPrice, String typeFlat,String lat,String lang,String productType) {

        mView.showProgress();
        if(uris.size()<=0){
            mModel.onFinished("Please Select photo first");
            mView.hideProgress();
        }else if(TextUtils.isEmpty(userId)){
            mModel.onFinished("Your Session is ended Please Logout And Login Again");
            mView.hideProgress();
        }else if(TextUtils.isEmpty(flatDescription)){
            mModel.onFinished("Please Write the Description");
            mView.hideProgress();
        }else if(TextUtils.isEmpty(flatLocation)){
            mModel.onFinished("Select Country");
            mView.hideProgress();
        }else if (TextUtils.isEmpty(flatPrice)){
            mModel.onFinished("please Write the Price of Product");
            mView.hideProgress();
        }else if(TextUtils.isEmpty(lat)|| TextUtils.isEmpty(lang)||Double.parseDouble(lang)==0||Double.parseDouble(lat)==0){
            mModel.onFinished("pleas Select Location");
            mView.hideProgress();
        }else if(typeFlat.equals("0")){
            mModel.onFinished("pleas Select Ownership type");
            mView.hideProgress();
        }else if(productType.equals("0")){
            mModel.onFinished("pleas Select Product Type");
            mView.hideProgress();
        }else {
            List<MultipartBody.Part> list = new ArrayList<>();
            int i = 0;
            Random rand = new Random();
            for (Uri uri : uris) {
                File file = new File( FileUtil.getPath(uri,context));
                //very important files[]
                final RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse("image/jpg"),
                                file
                        );

                int value = rand.nextInt(50);
                String filename =String.valueOf(value)+file.getName();
                MultipartBody.Part imageRequest = MultipartBody.Part.createFormData("files[]",filename, requestFile);
                list.add(imageRequest);
            }


            WebService.getInstance(true).getApi().uploadImages(list,
                    Integer.parseInt(userId),
                    flatDescription,
                    flatLocation,
                    Double.parseDouble(flatPrice),
                    Integer.parseInt(typeFlat),
                    Double.parseDouble(lat),
                    Double.parseDouble(lang),
                    String.valueOf(new java.sql.Date(System.currentTimeMillis())),
                    Integer.parseInt(productType)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Log.e(TAG, "onResponse: "+t.getLocalizedMessage() );
                    mView.hideProgress();
                }
            });

        }



    }

    public void performGetLatLang(double lat,double lang){
        mModel.getLatLang(lat,lang);
    }
}
