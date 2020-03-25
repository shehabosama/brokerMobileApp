package com.android.jobber.Ui.Activities.Login;

import com.android.jobber.common.model.User;

public interface LoginContract
{
    interface Model {
        interface onFinishedListener {
            void onFinished(User user);
            void onFinished(String result);
            void onFailuer(Throwable t);
        }
        void Login(onFinishedListener onFinishedListener,String email,String password);
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
        void performLogin(String email,String password);
        void performUpdateToken(String userId,String token);
    }
}
