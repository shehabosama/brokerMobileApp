package com.android.jobber.Ui.fragments.PrdouctRequestsFragment;

import com.android.jobber.common.model.RequestResponse;

public interface ProductRequestContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void loadRequestData(RequestResponse requestResponse);
            void onFailuer(Throwable t);
        }
    }

    interface View {
        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void performGetRequests(String userId);
        void performDeleteRequest(String requestId);
        void performConfirmation(String requestId,String userId,String confirmation,String productId,String token);
    }

}
