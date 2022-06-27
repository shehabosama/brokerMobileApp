package com.android.jobber.Ui.fragments.JobberAccountFragment;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.text.TextUtils;
import android.util.Log;

import com.android.jobber.common.model.Flats.FlatsResponse;
import com.android.jobber.common.model.MainResponse;
import com.android.jobber.common.model.MyFavoriteResponse;
import com.android.jobber.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterJobberAccount implements JobberAccountContract.Presenter {
    private JobberAccountContract.Model.onFinishedListener mModel;
    private JobberAccountContract.View mView;

    public PresenterJobberAccount(JobberAccountContract.Model.onFinishedListener mModel, JobberAccountContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void performGetUserFlats(final String userId) {
        mView.showProgress();
        if(TextUtils.isEmpty(userId)){
            mModel.onFinished("something went wrong..");
        }else{
            WebService.getInstance(true).getApi().getUserFlats(userId).enqueue(new Callback<FlatsResponse>() {
                @Override
                public void onResponse(Call<FlatsResponse> call, Response<FlatsResponse> response) {
                    performGetFavoriteProduct(userId, response.body());

                }

                @Override
                public void onFailure(Call<FlatsResponse> call, Throwable t) {

                    mModel.onFailuer(t);
                    mView.hideProgress();
                }
            });

        }

    }

    @Override
    public void performDeleteProduct(String userId) {
        if(TextUtils.isEmpty(userId)){
            mModel.onFinished("something went wrong..");
        }else{
            WebService.getInstance(true).getApi().deleteProduct(Integer.parseInt(userId)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {

                    mModel.onFailuer(t);
                    mView.hideProgress();
                }
            });
        }
    }

    @Override
    public void performGetFavoriteProduct(String user_id, final FlatsResponse flatsResponse) {
        mView.showProgress();

        if(TextUtils.isEmpty(user_id)){
            mModel.onFinished("Please Make sure are you blocked or not");
            mView.hideProgress();
        }else{
            WebService.getInstance(false).getApi().getAllFavorites(Integer.parseInt(user_id)).enqueue(new Callback<MyFavoriteResponse>() {
                @Override
                public void onResponse(Call<MyFavoriteResponse> call, Response<MyFavoriteResponse> response) {
                    //  mModel.loadFavoritesCarList(response.body());
                    mModel.loadAllFlats(flatsResponse,response.body());
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MyFavoriteResponse> call, Throwable t) {
                    mModel.onFinished(t.getLocalizedMessage());
                }
            });
        }
    }


    @Override
    public void performAddItemFavorite(String userId, String productId) {

        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(productId)){
            mModel.onFinished("Something Went Wrong...");
        }else{
            WebService.getInstance(true).getApi().FevoriteOpration(Integer.parseInt(userId),Integer.parseInt(productId)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {

                    mModel.onFinished(response.body().message);
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {

                }
            });
        }
    }


    @Override
    public void performOrderRequest(String senderId, String receiverId, String orderId, String token,String timesTamp) {
        if(TextUtils.isEmpty(senderId)||TextUtils.isEmpty(receiverId)||TextUtils.isEmpty(orderId)||TextUtils.isEmpty(token)){
            mModel.onFinished("Something Went Wrong..");
        }else{
            WebService.getInstance(true).getApi().addOrderRequest(senderId,receiverId,orderId,token,timesTamp).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {

                    assert response.body() != null;
                    mModel.onFinished(response.body().message);
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void performRateUs(String normalUsers, String adminUsers, String rateDegree, String reviewDescription) {
        WebService.getInstance(true).getApi().rateUs(Integer.parseInt(normalUsers),Integer.parseInt(adminUsers),rateDegree,reviewDescription).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                mModel.onFinished(response.body().message);
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
            }
        });
    }

    @Override
    public void performDeleteItemFavorite(String userId, String productId, String delete) {
        mView.showProgress();
        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(productId)){
            mModel.onFinished("Something Went Wrong...");
            mView.hideProgress();
        }else{
            WebService.getInstance(true).getApi().FevoriteOpration(Integer.parseInt(userId),Integer.parseInt(productId),delete).enqueue(new Callback<MainResponse>() {
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
