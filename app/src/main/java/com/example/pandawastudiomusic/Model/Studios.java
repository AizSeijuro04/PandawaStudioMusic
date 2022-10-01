package com.example.pandawastudiomusic.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Studios implements Parcelable {
    private String name,username,password, studiosID;
    private Long rating;

    public Studios() {
    }

    protected Studios(Parcel in) {
        name = in.readString();
        username = in.readString();
        password = in.readString();
        studiosID = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readLong();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(studiosID);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(rating);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Studios> CREATOR = new Creator<Studios>() {
        @Override
        public Studios createFromParcel(Parcel in) {
            return new Studios(in);
        }

        @Override
        public Studios[] newArray(int size) {
            return new Studios[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getStudiosID() {
        return studiosID;
    }

    public void setStudiosID(String studiosID) {
        this.studiosID = studiosID;
    }
}
