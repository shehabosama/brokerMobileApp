package com.android.jobber.Ui.Activities.ResetPasswordActivity;

import android.text.TextUtils;

import com.android.jobber.common.model.MainResponse;
import com.android.jobber.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterResetPassword implements ResetPasswordContract.Presenter {
    private ResetPasswordContract.View mView;
    private ResetPasswordContract.Model.onFinishedListener mModel;

    public PresenterResetPassword(ResetPasswordContract.Model.onFinishedListener mModel, ResetPasswordContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performResetPassword(String Password, String ConfirmPassword, String email) {
        mView.showProgress();
        if(TextUtils.isEmpty(Password)||TextUtils.isEmpty(ConfirmPassword)||TextUtils.isEmpty(email)) {
            mModel.onFinished("Please make sure to fill all the fields");
            mView.hideProgress();
        }else {
            if (!Password.equals(ConfirmPassword)) {
                mModel.onFinished("Password mismatch");
                mView.hideProgress();

            } else {

                WebService.getInstance(true).getApi().updatPassword(email,Password).enqueue(new Callback<MainResponse>() {
                    @Override
                    public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                        mModel.onFinished(String.valueOf(response.body().status));
                        mView.hideProgress();
                    }

                    @Override
                    public void onFailure(Call<MainResponse> call, Throwable t) {
                        mView.hideProgress();
                    }
                });
            }
        }
    }
}