package com.android.jobber.common.model.Flats;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Flat implements Parcelable {
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
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("verifying_mark")
    @Expose
    private String verifying_mark;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("flats_image")
    @Expose
    private List<FlatsImage> flatsImage = null;
    @SerializedName("rate")
    @Expose
    private int rate;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("product_type")
    @Expose
    private String product_type;
    @SerializedName("phone_no")
    @Expose
    private String phone_no;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("gender")
    @Expose
    private String gender;

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

    public static Creator<Flat> getCREATOR() {
        return CREATOR;
    }

    protected Flat(Parcel in) {
        id = in.readString();
        userId = in.readString();
        flatDscription = in.readString();
        flatLocation = in.readString();
        typeFlat = in.readString();
        flatPrice = in.readString();
        publishDate = in.readString();
        username = in.readString();
        email = in.readString();
        userImage = in.readString();
        lat = in.readString();
        lang = in.readString();
        product_type = in.readString();
        phone_no = in.readString();
        token = in.readString();
    }

    public static final Creator<Flat> CREATOR = new Creator<Flat>() {
        @Override
        public Flat createFromParcel(Parcel in) {
            return new Flat(in);
        }

        @Override
        public Flat[] newArray(int size) {
            return new Flat[size];
        }
    };

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
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

    public List<FlatsImage> getFlatsImage() {
        return flatsImage;
    }

    public void setFlatsImage(List<FlatsImage> flatsImage) {
        this.flatsImage = flatsImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(userId);
        parcel.writeString(flatDscription);
        parcel.writeString(flatLocation);
        parcel.writeString(typeFlat);
        parcel.writeString(flatPrice);
        parcel.writeString(publishDate);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(userImage);
        parcel.writeString(lat);
        parcel.writeString(lang);
        parcel.writeString(product_type);
        parcel.writeString(phone_no);
        parcel.writeString(token);

    }
}