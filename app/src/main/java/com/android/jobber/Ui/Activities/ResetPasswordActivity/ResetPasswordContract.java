package com.android.jobber.Ui.Activities.ResetPasswordActivity;

public interface ResetPasswordContract {

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
        void performResetPassword(String Password,String ConfirmPassword,String email);
    }

}
