package com.android.jobber.common.model;

import com.android.jobber.common.model.Flats.FlatsImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductRequest {
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("sender_user_name")
    @Expose
    private String sender_user_name;
    @SerializedName("sender_user_image")
    @Expose
    private String sender_user_image;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receive_user_id")
    @Expose
    private String receiveUserId;
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("sender_token")
    @Expose
    private String senderToken;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("flat_dscription")
    @Expose
    private String flatDscription;
    @SerializedName("flat_location")
    @Expose
    private String flatLocation;
    @SerializedName("type_flat")
    @Expose
    private String typeFlat;
    @SerializedName("flat_price")
    @Expose
    private String flatPrice;
    @SerializedName("publish_date")
    @Expose
    private String publishDate;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("flats_image")
    @Expose
    private List<FlatsImage> flatsImage = null;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender_user_name() {
        return sender_user_name;
    }

    public void setSender_user_name(String sender_user_name) {
        this.sender_user_name = sender_user_name;
    }

    public String getSender_user_image() {
        return sender_user_image;
    }

    public void setSender_user_image(String sender_user_image) {
        this.sender_user_image = sender_user_image;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getSenderToken() {
        return senderToken;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlatDscription() {
        return flatDscription;
    }

    public void setFlatDscription(String flatDscription) {
        this.flatDscription = flatDscription;
    }

    public String getFlatLocation() {
        return flatLocation;
    }

    public void setFlatLocation(String flatLocation) {
        this.flatLocation = flatLocation;
    }

    public String getTypeFlat() {
        return typeFlat;
    }

    public void setTypeFlat(String typeFlat) {
        this.typeFlat = typeFlat;
    }

    public String getFlatPrice() {
        return flatPrice;
    }

    public void setFlatPrice(String flatPrice) {
        this.flatPrice = flatPrice;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<FlatsImage> getFlatsImage() {
        return flatsImage;
    }

    public void setFlatsImage(List<FlatsImage> flatsImage) {
        this.flatsImage = flatsImage;
    }

}