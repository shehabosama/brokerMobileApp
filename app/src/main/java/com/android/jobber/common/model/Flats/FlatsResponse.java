package com.android.jobber.common.model.Flats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlatsResponse
{

    @SerializedName("flats")
    @Expose
    private List<Flat> flats = null;

    public List<Flat> getFlats() {
        return flats;
    }

    public void setFlats(List<Flat> flats) {
        this.flats = flats;
    }

}

