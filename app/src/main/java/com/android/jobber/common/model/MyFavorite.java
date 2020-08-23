package com.android.jobber.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.jobber.common.model.Flats.FlatsImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyFavorite implements Parcelable {
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("fav_id")
    @Expose
    private String favId;
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
    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("flat_image")
    @Expose
    private List<FlatsImage> flatImage = null;
    @SerializedName("rate")
    @Expose
    private int rate;
    @SerializedName("verifying_mark")
    @Expose
    private String verifying_mark;

    public String getVerifying_mark() {
        return verifying_mark;
    }

    public void setVerifying_mark(String verifying_mark) {
        this.verifying_mark = verifying_mark;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }


    protected MyFavorite(Parcel in) {

        favId = in.readString();
        username = in.readString();
        email = in.readString();
        userImage = in.readString();
        phoneNo = in.readString();
        token = in.readString();
        address = in.readString();
        gender = in.readString();
        id = in.readString();
        flatDscription = in.readString();
        flatLocation = in.readString();
        typeFlat = in.readString();
        flatPrice = in.readString();
        publishDate = in.readString();
        lat = in.readString();
        lang = in.readString();
        productType = in.readString();
        verifying_mark = in.readString();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public static final Creator<MyFavorite> CREATOR = new Creator<MyFavorite>() {
        @Override
        public MyFavorite createFromParcel(Parcel in) {
            return new MyFavorite(in);
        }

        @Override
        public MyFavorite[] newArray(int size) {
            return new MyFavorite[size];
        }
    };

    public String getFavId() {
        return favId;
    }

    public void setFavId(String favId) {
        this.favId = favId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<FlatsImage> getFlatImage() {
        return flatImage;
    }

    public void setFlatImage(List<FlatsImage> flatImage) {
        this.flatImage = flatImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(favId);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(userImage);
        parcel.writeString(phoneNo);
        parcel.writeString(token);
        parcel.writeString(address);
        parcel.writeString(gender);
        parcel.writeString(id);
        parcel.writeString(flatDscription);
        parcel.writeString(flatLocation);
        parcel.writeString(typeFlat);
        parcel.writeString(flatPrice);
        parcel.writeString(publishDate);
        parcel.writeString(lat);
        parcel.writeString(lang);
        parcel.writeString(productType);
        parcel.writeString(verifying_mark);
    }
}