package com.android.jobber.Ui.fragments.PrdouctRequestsFragment;

import android.content.Context;
import android.text.TextUtils;

import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.model.MainResponse;
import com.android.jobber.common.model.RequestResponse;
import com.android.jobber.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterPrdouctRequest implements ProductRequestContract.Presenter {
    private ProductRequestContract.View mView;
    private ProductRequestContract.Model.onFinishedListener mModel;
    private Context context;

    public PresenterPrdouctRequest(ProductRequestContract.Model.onFinishedListener mModel, ProductRequestContract.View mView,Context context) {
        this.mView = mView;
        this.mModel = mModel;
        this.context  = context;
    }


    @Override
    public void performGetRequests(String userId) {
        mView.showProgress();
        if(AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,context,false)){
            if(TextUtils.isEmpty(userId)){
                mModel.onFinished("Something Went Wrong...");
                mView.hideProgress();
            }else{
                WebService.getInstance(true).getApi().getReceiverUserRequest(Integer.parseInt(userId)).enqueue(new Callback<RequestResponse>() {
                    @Override
                    public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                        mModel.loadRequestData(response.body());

                    }

                    @Override
                    public void onFailure(Call<RequestResponse> call, Throwable t) {
                        mView.hideProgress();
                    }
                });
            }
        }else{
            WebService.getInstance(true).getApi().getSenderUserRequest(Integer.parseInt(userId)).enqueue(new Callback<RequestResponse>() {
                @Override
                public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                    mModel.loadRequestData(response.body());

                }

                @Override
                public void onFailure(Call<RequestResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });
        }

    }

    @Override
    public void performDeleteRequest(String requestId) {
        mView.showProgress();
        if(TextUtils.isEmpty(requestId)){
            mModel.onFinished("Something Went Wrong");
            mView.hideProgress();

        }else{
            WebService.getInstance(true).getApi().deleteOrderRequest(Integer.parseInt(requestId)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();

                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mView.hideProgress();


                }
            });
        }
    }

    @Override
    public void performConfirmation(String requestId,String userId,String confirmation,String productId,String token) {
        mView.showProgress();
        if(TextUtils.isEmpty(requestId)){
            mModel.onFinished("Something Went Wrong");
            mView.hideProgress();
        }else{
            WebService.getInstance(true).getApi().updateRequestStatus(Integer.parseInt(userId),confirmation,productId,requestId,token).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
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
