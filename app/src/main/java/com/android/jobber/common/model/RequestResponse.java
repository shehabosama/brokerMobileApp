package com.android.jobber.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestResponse {

    @SerializedName("product_request")
    @Expose
    private List<ProductRequest> productRequest = null;

    public List<ProductRequest> getProductRequest() {
        return productRequest;
    }

    public void setProductRequest(List<ProductRequest> productRequest) {
        this.productRequest = productRequest;
    }

}