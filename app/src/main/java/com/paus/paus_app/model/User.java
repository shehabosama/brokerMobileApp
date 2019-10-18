package com.paus.paus_app.model;

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

    public int id ;
    public boolean isAdmin;

}
