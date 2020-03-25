package com.android.jobber.Ui.fragments.ReviewFragment;

import com.android.jobber.common.model.ReviewResponse;

public interface ReviewContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);

            void onFailuer(Throwable t);
            void loadReviews(ReviewResponse reviewResponse);
        }
    }

    interface View {
        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void performGetReviews(String admin_user_id);
    }

}
