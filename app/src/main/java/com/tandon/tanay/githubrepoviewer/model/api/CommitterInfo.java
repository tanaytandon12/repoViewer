package com.tandon.tanay.githubrepoviewer.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

/**
 * Created by tanaytandon on 22/09/17.
 */

public class CommitterInfo implements Parcelable {

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

    public CommitterInfo() {
    }

    protected CommitterInfo(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.date = (DateTime) in.readSerializable();
    }

    public static final Parcelable.Creator<CommitterInfo> CREATOR = new Parcelable.Creator<CommitterInfo>() {
        @Override
        public CommitterInfo createFromParcel(Parcel source) {
            return new CommitterInfo(source);
        }

        @Override
        public CommitterInfo[] newArray(int size) {
            return new CommitterInfo[size];
        }
    };
}
