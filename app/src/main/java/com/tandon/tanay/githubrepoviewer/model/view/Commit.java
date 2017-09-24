package com.tandon.tanay.githubrepoviewer.model.view;


import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

public class Commit implements Parcelable {

    public String sha;

    public String message;

    public String url;

    public String avatarUrl;

    public String userLogin;

    public DateTime commitDateTime;

    public String repoName;

    public String ownerName;

    public Boolean isFavourite;

    public Commit(String sha, String message, String url, String avatarUrl, String userLogin,
                  DateTime commitDateTime, String repoName, String ownerName, Boolean isFavourite) {
        this.sha = sha;
        this.message = message;
        this.url = url;
        this.avatarUrl = avatarUrl;
        this.userLogin = userLogin;
        this.commitDateTime = commitDateTime;
        this.repoName = repoName;
        this.ownerName = ownerName;
        this.isFavourite = isFavourite;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sha);
        dest.writeString(this.message);
        dest.writeString(this.url);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.userLogin);
        dest.writeSerializable(this.commitDateTime);
        dest.writeString(this.repoName);
        dest.writeString(this.ownerName);
        dest.writeValue(this.isFavourite);
    }

    protected Commit(Parcel in) {
        this.sha = in.readString();
        this.message = in.readString();
        this.url = in.readString();
        this.avatarUrl = in.readString();
        this.userLogin = in.readString();
        this.commitDateTime = (DateTime) in.readSerializable();
        this.repoName = in.readString();
        this.ownerName = in.readString();
        this.isFavourite = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Commit> CREATOR = new Parcelable.Creator<Commit>() {
        @Override
        public Commit createFromParcel(Parcel source) {
            return new Commit(source);
        }

        @Override
        public Commit[] newArray(int size) {
            return new Commit[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Commit)) {
            return false;
        }
        Commit commit = (Commit) obj;
        return this.sha.equals(commit.sha);
    }
}
