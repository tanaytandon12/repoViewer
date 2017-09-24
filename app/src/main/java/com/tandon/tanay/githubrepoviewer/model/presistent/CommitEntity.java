package com.tandon.tanay.githubrepoviewer.model.presistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.joda.time.DateTime;


@Entity
public class CommitEntity {


    @Id
    private Long id;

    private String sha;

    private String message;

    private String url;

    private String avatarUrl;

    private String userLogin;

    private String repoName;

    private String ownerName;

    private Long timestamp;

    private Boolean isFavourite;


    @Generated(hash = 1112228540)
    public CommitEntity(Long id, String sha, String message, String url,
            String avatarUrl, String userLogin, String repoName, String ownerName,
            Long timestamp, Boolean isFavourite) {
        this.id = id;
        this.sha = sha;
        this.message = message;
        this.url = url;
        this.avatarUrl = avatarUrl;
        this.userLogin = userLogin;
        this.repoName = repoName;
        this.ownerName = ownerName;
        this.timestamp = timestamp;
        this.isFavourite = isFavourite;
    }

    @Generated(hash = 166539517)
    public CommitEntity() {
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getSha() {
        return this.sha;
    }

    public Long getId() {
        return this.id;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserLogin() {
        return this.userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getRepoName() {
        return this.repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getIsFavourite() {
        return this.isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }


}
