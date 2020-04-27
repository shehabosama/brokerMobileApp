package com.android.jobber.Ui.Activities.RegisterActivity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.jobber.common.model.MainResponse;
import com.android.jobber.common.model.User;
import com.android.jobber.common.network.WebService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterRegister implements RegisterContract.Presenter , RegisterContract.Model.onFinishedListener  {

    private RegisterContract.View mLoginView;
    private Context context;
    PresenterRegister(RegisterContract.View mLoginView , Context context) {
        this.mLoginView = mLoginView;
        this.context = context;
    }

    @Override
    public void performRegister(String userName, final String email, final String password, String confirmPassword,  String isAdmin,String phoneNo,String userType) {
        mLoginView.showProgress();

        if (TextUtils.isEmpty(userName) ||
                TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(isAdmin)
        ) {
            mLoginView.loginValidations();
        } else if (!password.equals(confirmPassword)) {
            mLoginView.loginValidations();
        } else if(!isEmailValid(email)){
            mLoginView.emailInvalid();
        } else if(TextUtils.isEmpty(userType)){
            mLoginView.loginValidations();
        }else {
            User user = new User();
            user.username = userName;
            user.email = email;
            user.password = password;
            user.phoneNo = Integer.parseInt(phoneNo);
            user.userType = userType;
            WebService.getInstance(true).getApi().registerUser(user).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    if (response.body().status == 2) {
                        mLoginView.hideProgress();

                    } else if (response.body().status == 1) {
                        Log.d(TAG, "onResponse: "+response.body().message);
                        mLoginView.hideProgress();
                        mLoginView.loginSuccess();
                    }
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mLoginView.hideProgress();
                    mLoginView.loginError();
                }
            });
        }
    }


    private boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);


        return matcher.matches();
    }


    @Override
    public void onFinished(User user) {
        mLoginView.setUserName(user.username);
    }

    @Override
    public void onFailuer(Throwable t) {

    }
}
