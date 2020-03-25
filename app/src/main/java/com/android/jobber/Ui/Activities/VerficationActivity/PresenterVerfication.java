package com.android.jobber.Ui.Activities.VerficationActivity;

import android.text.TextUtils;

import com.android.jobber.common.model.verificationResponse;
import com.android.jobber.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterVerfication implements VerificationContract.Presenter {
    private VerificationContract.View mView;
    private VerificationContract.Model.onFinishedListener mModel;

    public PresenterVerfication(VerificationContract.Model.onFinishedListener mModel, VerificationContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performVerification(String code, String email) {
        mView.showProgress();
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(code)){
            mModel.onFinished("Please fill all Fields");
            mView.hideProgress();
        }else{
            WebService.getInstance(false).getApi().reciveVerification(email,code).enqueue(new Callback<verificationResponse>() {
                @Override
                public void onResponse(Call<verificationResponse> call, Response<verificationResponse> response) {

                    if(response.body().error.equals("0")){
                        mModel.onFinished(response.body().error);
                        mView.hideProgress();
                    }else{
                        mModel.onFinished(response.body().error);
                        mView.hideProgress();
                    }
                }

                @Override
                public void onFailure(Call<verificationResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });
        }

    }
}