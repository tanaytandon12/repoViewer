package com.tandon.tanay.githubrepoviewer.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tanaytandon on 22/09/17.
 */

public class CommitInfo implements Parcelable {

    @SerializedName("author")
    public AuthorInfo authorInfo;

    @SerializedName("committer")
    public CommitterInfo committerInfo;

    public String message;

    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.authorInfo, flags);
        dest.writeParcelable(this.committerInfo, flags);
        dest.writeString(this.message);
        dest.writeString(this.url);
    }

    public CommitInfo() {
    }

    protected CommitInfo(Parcel in) {
        this.authorInfo = in.readParcelable(AuthorInfo.class.getClassLoader());
        this.committerInfo = in.readParcelable(CommitterInfo.class.getClassLoader());
        this.message = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<CommitInfo> CREATOR = new Parcelable.Creator<CommitInfo>() {
        @Override
        public CommitInfo createFromParcel(Parcel source) {
            return new CommitInfo(source);
        }

        @Override
        public CommitInfo[] newArray(int size) {
            return new CommitInfo[size];
        }
    };
}
