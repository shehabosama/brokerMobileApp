package com.android.jobber.common.model;

import com.google.gson.annotations.SerializedName;

/*
 This class we can fetch status and message We are use it as object
*/
public class MainResponse {
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
}
