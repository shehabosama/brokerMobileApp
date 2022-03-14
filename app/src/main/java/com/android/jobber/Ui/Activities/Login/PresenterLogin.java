package com.android.jobber.Ui.Activities.Login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.model.LoginResponse;
import com.android.jobber.common.model.MainResponse;
import com.android.jobber.common.model.User;
import com.android.jobber.common.network.WebService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jaiselrahman.filepicker.activity.FilePickerActivity.TAG;

public class PresenterLogin implements LoginContract.Presenter {

    private LoginContract.View mLoginView;
    private LoginContract.Model.onFinishedListener mModel;
    private Context context;
    PresenterLogin(LoginContract.View mLoginView,LoginContract.Model.onFinishedListener mModel , Context context ) {
        this.mLoginView = mLoginView;
        this.context = context;
        this.mModel = mModel;
    }

    @Override
    public void performLogin(final String email, final String password) {

        if(TextUtils.isEmpty(email))
        {
            mLoginView.loginValidations();
        }else if (TextUtils.isEmpty(password))
        {
               mLoginView.loginValidations();
        }else if(!isEmailValid(email)){
            mLoginView.emailInvalid();
        }else
        {
            final User user = new User();
            user.email = email;
            user.password = password;

            WebService.getInstance(true).getApi().loginUser(user).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    assert response.body() != null;
                    if (response.body().status == 0) {
                        mLoginView.loginError();

                    } else if (response.body().status == 1) {
                        user.username = response.body().user.username;
                        user.id = Integer.parseInt(response.body().user.id);
                        user.isAdmin = response.body().user.is_admin.equals("1");
                        user.phoneNo = response.body().user.phoneNo;
                        user.address = response.body().user.address;
                        user.gender   = response.body().user.gender;
                        user.userImage  = response.body().user.userImage;
                        user.verification_code = response.body().user.verification_code;

                        mModel.onFinished(user);

                        getUserToken(String.valueOf(user.id));
                        AppPreferences.setString(Constants.AppPreferences.LOGGED_IN_USER_KEY,String.valueOf(user.id),context);
                        if (user.isAdmin) {
                            AppPreferences.setBoolean(Constants.AppPreferences.IS_ADMIN,true,context);
                       } else {
                            AppPreferences.setBoolean(Constants.AppPreferences.IS_ADMIN, false,context);
                        }

                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    mLoginView.loginError();
                    mLoginView.hideProgress();
                    mModel.onFailuer(t);
                    Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
                }
            });
        }
    }

    @Override
    public void performUpdateToken(String userId, String token) {
        Log.e(TAG, "performUpdateToken: "+token +" id: "+userId );
        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(token)){
            mModel.onFinished("Something Went Wrong...");
        }else{
            WebService.getInstance(true).getApi().updateToken(Integer.parseInt(userId),token).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+ t.getLocalizedMessage() );
                        mModel.onFinished(t.getMessage());
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

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);


        return matcher.matches();
    }

    private void getUserToken(final String userId) {

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();
                    Log.e(TAG, "onComplete: token: "+ token);
                    AppPreferences.setString(Constants.AppPreferences.USER_TOKEN, token, context);
                    performUpdateToken(userId,token);

                }
            }

        });
    }


}
