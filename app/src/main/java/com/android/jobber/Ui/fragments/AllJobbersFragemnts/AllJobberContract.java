package com.android.jobber.Ui.fragments.AllJobbersFragemnts;

import com.android.jobber.common.model.JobberUsersResponse;

public interface AllJobberContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void loadAllJobberUsers(JobberUsersResponse jobberUsersResponse);
            void onFailuer(Throwable t);

        }
    }

    interface View {
        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void performGetAllJobber();
    }

}
