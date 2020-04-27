package com.android.jobber.common.model;

import com.google.gson.annotations.SerializedName;



/*
Fetch the user name and password and email through setter and getter function we are use it as object
*/
public class User  {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("email")
    public String email;

   // @SerializedName("id")
    public int id ;
    //@SerializedName("is_admin")
    public boolean isAdmin;
    @SerializedName("address")
    public String address;
    @SerializedName("user_image")
    public String userImage;
    @SerializedName("phone_no")
    public int phoneNo;
    @SerializedName("gender")
    public int gender;
    @SerializedName("user_type")
    public String userType;
}
