package com.paus.paus_app.WebServiceapi;





import com.paus.paus_app.model.LoginResponse;
import com.paus.paus_app.model.MainResponse;
import com.paus.paus_app.model.User;
import com.paus.paus_app.model.verificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface API {

    @POST("Login_user.php")
    Call<LoginResponse> loginUser(@Body User user);//to send request and response the message if the account is login or not

    @POST("register_user.php")
    Call<MainResponse> registerUser(@Body User user);// to send request and response the message if the user inserted in database or not
    @FormUrlEncoded
    @POST("index.php")
    Call<verificationResponse> sendVerification(@Field("email") String email);

    @FormUrlEncoded
    @POST("verification.php")
    Call<verificationResponse> reciveVerification(@Field("email") String email ,@Field("random") String verification);
    @FormUrlEncoded
    @POST("ResetPassword.php")
    Call<MainResponse> updatPassword(@Field("email") String email,@Field("password")String password) ;//to send request and response the message if the post update or not




}
