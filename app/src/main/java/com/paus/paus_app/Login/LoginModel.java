package com.paus.paus_app.Login;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.paus.paus_app.common.network.WebService;
import com.paus.paus_app.common.model.LoginResponse;
import com.paus.paus_app.common.model.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LoginModel implements LoginContract.Presenter , LoginContract.Model.onFinishedListener {

    private LoginContract.View mLoginView;
    LoginModel(LoginContract.View mLoginView) {
        this.mLoginView = mLoginView;
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

            WebService.getInstance().getApi().loginUser(user).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    assert response.body() != null;
                    if (response.body().status == 0) {
                        mLoginView.loginError();

                    } else if (response.body().status == 1) {
                        user.username = response.body().user.username;
                        user.id = Integer.parseInt(response.body().user.id);
                        user.isAdmin = response.body().user.is_admin.equals("1");
                        onFinished(user);
                        mLoginView.loginSuccess();

//                        if (user.isAdmin) {
//
//                        } else {
//
//                        }

                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    mLoginView.loginError();
                    onFailuer(t);
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


    @Override
    public void onFinished(User user) {
        mLoginView.setUserName(user.username);
    }

    @Override
    public void onFailuer(Throwable t) {

    }
}
