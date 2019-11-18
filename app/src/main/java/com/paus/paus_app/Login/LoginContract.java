package com.paus.paus_app.Login;

import com.paus.paus_app.model.User;

public interface LoginContract
{
    interface Model {
        interface onFinishedListener {
            void onFinished(User user);
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
    }
}
