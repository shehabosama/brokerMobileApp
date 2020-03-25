package com.android.jobber.Ui.Activities.ForgetPasswordActivity;

import android.text.TextUtils;

import com.android.jobber.common.model.verificationResponse;
import com.android.jobber.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterForgetPassword implements ForgetContract.Presenter {
    private ForgetContract.View mView;
    private ForgetContract.Model.onFinishedListener mModel;

    public PresenterForgetPassword(ForgetContract.Model.onFinishedListener mModel, ForgetContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performSendVerification(String email) {
        mView.showProgress();
        if(TextUtils.isEmpty(email)){
            mModel.onFinished("Please Write your email..");
            mView.hideProgress();
        }else{
            WebService.getInstance(false).getApi().sendVerification(email).enqueue(new Callback<verificationResponse>() {
                @Override
                public void onResponse(Call<verificationResponse> call, Response<verificationResponse> response) {

                    mModel.onFinished(response.body().error);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<verificationResponse> call, Throwable t) {

                    mModel.onFinished(t.getLocalizedMessage());
                    mView.hideProgress();
                }
            });
        }
    }
}