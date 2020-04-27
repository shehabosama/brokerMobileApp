package com.android.jobber.Ui.Activities.RegisterActivity;

import com.android.jobber.common.model.User;

public interface RegisterContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(User user);
            void onFailuer(Throwable t);
        }
        void Login(onFinishedListener onFinishedListener, String email, String password);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void loginValidations();
        void loginSuccess();
        void loginError();
        void emailInvalid();
        void setUserName(String username);
    }
    interface Presenter{
        void performRegister(String userName, String email, String password, String confirmPassword, String isAdmin,String phoneNo,String user_type);
    }
}
