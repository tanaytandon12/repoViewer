package com.tandon.tanay.githubrepoviewer.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class CommitResponse implements Parcelable {

    public String sha;

    @SerializedName("commit")
    public CommitInfo commitInfo;

    public String url;

    public Author author;

    public Committer committer;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sha);
        dest.writeParcelable(this.commitInfo, flags);
        dest.writeString(this.url);
        dest.writeParcelable(this.author, flags);
        dest.writeParcelable(this.committer, flags);
    }

    public CommitResponse() {
    }

    protected CommitResponse(Parcel in) {
        this.sha = in.readString();
        this.commitInfo = in.readParcelable(CommitInfo.class.getClassLoader());
        this.url = in.readString();
        this.author = in.readParcelable(Author.class.getClassLoader());
        this.committer = in.readParcelable(Committer.class.getClassLoader());
    }

    public static final Parcelable.Creator<CommitResponse> CREATOR = new Parcelable.Creator<CommitResponse>() {
        @Override
        public CommitResponse createFromParcel(Parcel source) {
            return new CommitResponse(source);
        }

        @Override
        public CommitResponse[] newArray(int size) {
            return new CommitResponse[size];
        }
    };
}
