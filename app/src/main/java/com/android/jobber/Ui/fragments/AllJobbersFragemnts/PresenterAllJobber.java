package com.android.jobber.Ui.fragments.AllJobbersFragemnts;

import com.android.jobber.common.model.JobberUsersResponse;
import com.android.jobber.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterAllJobber implements AllJobberContract.Presenter {
    private AllJobberContract.View mView;
    private AllJobberContract.Model.onFinishedListener mModel;

    public PresenterAllJobber(AllJobberContract.Model.onFinishedListener mModel, AllJobberContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performGetAllJobber() {
        mView.showProgress();
        WebService.getInstance(true).getApi().getAllJobberUsers().enqueue(new Callback<JobberUsersResponse>() {
            @Override
            public void onResponse(Call<JobberUsersResponse> call, Response<JobberUsersResponse> response) {
                mModel.loadAllJobberUsers(response.body());

            }

            @Override
            public void onFailure(Call<JobberUsersResponse> call, Throwable t) {
                mView.hideProgress();
            }
        });
    }
}