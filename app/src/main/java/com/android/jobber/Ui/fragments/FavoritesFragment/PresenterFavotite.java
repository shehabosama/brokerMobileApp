package com.android.jobber.Ui.fragments.FavoritesFragment;

import android.text.TextUtils;
import android.util.Log;

import com.android.jobber.common.model.MainResponse;
import com.android.jobber.common.model.MyFavoriteResponse;
import com.android.jobber.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterFavotite implements FavoriteContract.Presenter {
    private FavoriteContract.View mView;
    private FavoriteContract.Model.onFinishedListener mModel;

    public PresenterFavotite(FavoriteContract.Model.onFinishedListener mModel, FavoriteContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performMyFavorites(String user_id) {
        mView.showProgress();
        if(TextUtils.isEmpty(user_id)){
            mModel.onFinished("Something Went Wrong");
            mView.hideProgress();
        }else{
            WebService.getInstance(true).getApi().getAllFavorites(Integer.parseInt(user_id)).enqueue(new Callback<MyFavoriteResponse>() {
                @Override
                public void onResponse(Call<MyFavoriteResponse> call, Response<MyFavoriteResponse> response) {
                    mModel.getMyFavorite(response.body());

                }

                @Override
                public void onFailure(Call<MyFavoriteResponse> call, Throwable t) {

                    Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
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
    public void performDeleteItemFavorite(String userId, String productId, String delete) {

        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(productId)){
            mModel.onFinished("Something Went Wrong...");
        }else{
            WebService.getInstance(true).getApi().FevoriteOpration(Integer.parseInt(userId),Integer.parseInt(productId),delete).enqueue(new Callback<MainResponse>() {
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
}