package com.android.jobber.common.model.Flats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlatsImage {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image_name")
    @Expose
    private String imageName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

}