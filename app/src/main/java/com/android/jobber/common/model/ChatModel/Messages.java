package com.android.jobber.common.model.ChatModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Messages implements Parcelable
{
    @SerializedName("id")
    private String id;
    @SerializedName("room_id")
    private String room_id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("type")
    private String type;
    @SerializedName("content")
    private String content;
    @SerializedName("timestamp")
    private String timestamp;

    public Messages(){}
    protected Messages(Parcel in) {
        id = in.readString();
        room_id = in.readString();
        user_name = in.readString();
        user_id = in.readString();
        type = in.readString();
        content = in.readString();
        timestamp = in.readString();
    }

    public static final Creator<Messages> CREATOR = new Creator<Messages>() {
        @Override
        public Messages createFromParcel(Parcel in) {
            return new Messages(in);
        }

        @Override
        public Messages[] newArray(int size) {
            return new Messages[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp()
    {
        if (timestamp == null) {
            return "now";
        } else {
            SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            Date date = null;
            try {
                date = serverFormat.parse(timestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            TimeZone timeZone = TimeZone.getDefault();
            int rawOffset = timeZone.getRawOffset();
            long local = 0;
            if (date != null) {
                local = date.getTime() + rawOffset;
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(local);
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            return format.format(calendar.getTime());
        }


    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(room_id);
        dest.writeString(user_id);
        dest.writeString(user_name);
        dest.writeString(type);
        dest.writeString(content);
        dest.writeString(timestamp);
    }
}
