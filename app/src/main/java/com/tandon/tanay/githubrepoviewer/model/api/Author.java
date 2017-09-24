package com.tandon.tanay.githubrepoviewer.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tanaytandon on 22/09/17.
 */

public class Author implements Parcelable {

    public String login;

    public Long id;

    @SerializedName("avatar_url")
    public String avatarUrl;

    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeValue(this.id);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.url);
    }

    public Author() {
    }

    protected Author(Parcel in) {
        this.login = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.avatarUrl = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Author> CREATOR = new Parcelable.Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel source) {
            return new Author(source);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
}
