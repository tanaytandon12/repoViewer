package com.tandon.tanay.githubrepoviewer.model.presistent;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RepoEntity {

    @Id
    private Long id;

    private String repoName;

    private String repoOwner;

    private Boolean active;

    @Generated(hash = 1480748687)
    public RepoEntity(Long id, String repoName, String repoOwner, Boolean active) {
        this.id = id;
        this.repoName = repoName;
        this.repoOwner = repoOwner;
        this.active = active;
    }

    @Generated(hash = 1231371780)
    public RepoEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRepoName() {
        return this.repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoOwner() {
        return this.repoOwner;
    }

    public void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
