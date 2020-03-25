package com.android.jobber.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyFavoriteResponse {

    @SerializedName("MyFavorite")
    @Expose
    private List<MyFavorite> myFavorite = null;

    public List<MyFavorite> getMyFavorite() {
        return myFavorite;
    }

    public void setMyFavorite(List<MyFavorite> myFavorite) {
        this.myFavorite = myFavorite;
    }

}