package com.android.jobber.Ui.Activities.VerficationActivity;

public interface VerificationContract {

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
        void performVerification(String code, String email);
    }

}
