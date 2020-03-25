package com.android.jobber.Ui.Activities.ForgetPasswordActivity;

public interface ForgetContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);

            void onFailuer(Throwable t);

        }
    }

    interface View {
        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void performSendVerification(String email);
    }

}
