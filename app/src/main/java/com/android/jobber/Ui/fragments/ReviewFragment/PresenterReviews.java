package com.android.jobber.Ui.fragments.ReviewFragment;

import android.text.TextUtils;

import com.android.jobber.common.model.ReviewResponse;
import com.android.jobber.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterReviews implements ReviewContract.Presenter {
    private ReviewContract.View mView;
    private ReviewContract.Model.onFinishedListener mModel;

    public PresenterReviews(ReviewContract.Model.onFinishedListener mModel, ReviewContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performGetReviews(String admin_user_id) {
        mView.showProgress();
        if(TextUtils.isEmpty(admin_user_id)){
            mModel.onFinished("Something Went Wrong..");
            mView.hideProgress();
        }else{
            WebService.getInstance(true).getApi().getRates(Integer.parseInt(admin_user_id)).enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                    mModel.loadReviews(response.body());

                }

                @Override
                public void onFailure(Call<ReviewResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });
        }
    }
}