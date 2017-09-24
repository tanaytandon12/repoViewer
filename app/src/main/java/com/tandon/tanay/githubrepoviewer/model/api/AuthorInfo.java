package com.tandon.tanay.githubrepoviewer.model.api;


import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

public class AuthorInfo implements Parcelable {

    public String name;

    public String email;

    public DateTime date;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeSerializable(this.date);
    }

    public AuthorInfo() {
    }

    protected AuthorInfo(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.date = (DateTime) in.readSerializable();
    }

    public static final Parcelable.Creator<AuthorInfo> CREATOR = new Parcelable.Creator<AuthorInfo>() {
        @Override
        public AuthorInfo createFromParcel(Parcel source) {
            return new AuthorInfo(source);
        }

        @Override
        public AuthorInfo[] newArray(int size) {
            return new AuthorInfo[size];
        }
    };
}
