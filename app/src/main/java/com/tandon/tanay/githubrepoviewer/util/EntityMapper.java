package com.tandon.tanay.githubrepoviewer.util;

import com.tandon.tanay.githubrepoviewer.model.api.CommitResponse;
import com.tandon.tanay.githubrepoviewer.model.presistent.CommitEntity;
import com.tandon.tanay.githubrepoviewer.model.view.Commit;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;


public enum EntityMapper {

    INSTANCE;

    public List<Commit> getCommitListFromEntityList(List<CommitEntity> commitEntities) {
        List<Commit> commits = new ArrayList<>();
        for (CommitEntity commitEntity : commitEntities) {
            commits.add(getCommitFromEntity(commitEntity));
        }
        return commits;
    }

    public Commit getCommitFromEntity(CommitEntity commitEntity) {
        return new Commit(commitEntity.getSha(), commitEntity.getMessage(),
                commitEntity.getUrl(), commitEntity.getAvatarUrl(), commitEntity.getUserLogin(),
                new DateTime(commitEntity.getTimestamp()), commitEntity.getRepoName(),
                commitEntity.getOwnerName(), commitEntity.getIsFavourite());

    }

    public List<Commit> getCommitListFromResponse(List<CommitResponse> commitResponses, String repoOwner,
                                                  String repoName) {
        List<Commit> commits = new ArrayList<>();
        for (CommitResponse commitResponse : commitResponses) {
            commits.add(getCommitFromResponse(commitResponse, repoOwner, repoName, false));
        }
        return commits;
    }

    public Commit getCommitFromResponse(CommitResponse commitResponse, String repoOwner,
                                        String repoName, boolean isFavourite) {
        return new Commit(commitResponse.sha, commitResponse.commitInfo.message, commitResponse.url,
                commitResponse.committer.avatarUrl, commitResponse.committer.login,
                commitResponse.commitInfo.committerInfo.date, repoName, repoOwner, isFavourite);
    }

    public List<CommitEntity> getEntitiesFromResponse(List<CommitResponse> commitResponseList,
                                                      String repoName, String repoOwner) {
        List<CommitEntity> commitEntityList = new ArrayList<>();
        for (CommitResponse commitResponse : commitResponseList) {
            commitEntityList.add(getCommitEntityFromResponse(commitResponse, repoName, repoOwner));
        }
        return commitEntityList;
    }

    public CommitEntity getCommitEntityFromResponse(CommitResponse commitResponse, String repoName,
                                                    String repoOwner) {
        CommitEntity commitEntity = new CommitEntity();
        commitEntity.setAvatarUrl(commitResponse.committer.avatarUrl);
        commitEntity.setIsFavourite(false);
        commitEntity.setMessage(commitResponse.commitInfo.message);
        commitEntity.setSha(commitResponse.sha);
        commitEntity.setUrl(commitResponse.url);
        commitEntity.setOwnerName(repoOwner);
        commitEntity.setRepoName(repoName);
        commitEntity.setUserLogin(commitResponse.committer.login);
        commitEntity.setTimestamp(new DateTime(commitResponse.commitInfo.committerInfo.date).getMillis());
        return commitEntity;
    }
}
