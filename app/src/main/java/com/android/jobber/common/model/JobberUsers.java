package com.android.jobber.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobberUsers implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String user_name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("user_image")
    @Expose
    private String user_image;
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
    @SerializedName("rate")
    @Expose
    private int rate;

    protected JobberUsers(Parcel in) {
        id = in.readInt();
        user_name = in.readString();
        email = in.readString();
        user_image = in.readString();
        phone_no = in.readString();
        token = in.readString();
        address = in.readString();
        gender = in.readString();
        rate = in.readInt();
    }

    public static final Creator<JobberUsers> CREATOR = new Creator<JobberUsers>() {
        @Override
        public JobberUsers createFromParcel(Parcel in) {
            return new JobberUsers(in);
        }

        @Override
        public JobberUsers[] newArray(int size) {
            return new JobberUsers[size];
        }
    };

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(user_name);
        parcel.writeString(email);
        parcel.writeString(user_image);
        parcel.writeString(phone_no);
        parcel.writeString(token);
        parcel.writeString(address);
        parcel.writeString(gender);
        parcel.writeInt(rate);
    }
}
