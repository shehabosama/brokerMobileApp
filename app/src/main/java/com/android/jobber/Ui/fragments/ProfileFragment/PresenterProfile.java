package com.android.jobber.Ui.fragments.ProfileFragment;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.HelperStuffs.FileUtil;
import com.android.jobber.common.SqlHelper.myDbAdapter;
import com.android.jobber.common.model.MainResponse;
import com.android.jobber.common.network.WebService;

import java.io.File;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterProfile implements ProfileContract.Presenter{
    ProfileContract.View mView;
    ProfileContract.Model.onFinishedListener mModel;
    myDbAdapter myDbAdapter;

    public PresenterProfile(ProfileContract.View mView, ProfileContract.Model.onFinishedListener mModel,myDbAdapter myDbAdapter) {
        this.mView = mView;
        this.mModel = mModel;
        this.myDbAdapter = myDbAdapter;
    }

    @Override
    public void performUpdateInformation(final String userId, final String userName, final String userAddress, final String gender, final String phoneNumber, final String email, final String image,String password) {
        mView.showProgress();
        WebService.getInstance(true).getApi().updateUserInformation(Integer.parseInt(userId),userName,userAddress,gender,phoneNumber,password).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {

                if (response.body().status == 200){
                    mModel.onFinished(response.body().message);
                    myDbAdapter.updateName(email,userName,userAddress,gender,image,phoneNumber);
                    mView.hideProgress();

                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

                mView.hideProgress();
            }
        });

    }

    @Override
    public void performUpdateProfilePhoto(Uri uri, String userId, Context context, final String phone , final String email, final String gender, final String name , final String address) {
        mView.showProgress();
        if(uri == null){
            mModel.onFinished("Pleas Select the photo");
        }else if(TextUtils.isEmpty(userId)){
            mModel.onFinished("Something Went Wrong..");
        }else{
            String cookie = "cookiehere";
            String stringValue = "stringValue";
            File file = new File( FileUtil.getPath(uri,context));
            final RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("image/jpg"),
                            file
                    );
            String items = "[1,2,4]";
            Random rand = new Random();
            int value = rand.nextInt(50);
            final String filename =String.valueOf(value)+file.getName();
            MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", filename, requestFile);


            RequestBody items1 = RequestBody.create(MediaType.parse("application/json"), items);
            RequestBody stringValue1 = RequestBody.create(MediaType.parse("text/plain"), stringValue);
            WebService.getInstance(true).getApi().updateProfileImage(cookie, body, items1, stringValue1, AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,context,"0")).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    if (response.body().status == 2){
                        mModel.onFinished(response.body().message);
                        mView.hideProgress();
                     //   Log.e(TAG, "onResponse: "+filename );
                       // myDbAdapter.updateName(email,name,address,gender,filename,phone);
                    } else {
                        Log.e(TAG, "onResponse2: "+filename );
                        mModel.onFinished(response.body().message);
                        mView.hideProgress();
                        myDbAdapter.updateName(email,name,address,gender,filename,phone);
                    }
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mModel.onFailuer(t);
                    mView.hideProgress();
                    Log.e(TAG, "onResponse: "+t.getLocalizedMessage());
                }
            });
        }

    }
}
