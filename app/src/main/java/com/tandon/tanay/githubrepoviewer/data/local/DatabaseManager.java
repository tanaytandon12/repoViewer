package com.tandon.tanay.githubrepoviewer.data.local;


import com.tandon.tanay.githubrepoviewer.constants.DbConfig;
import com.tandon.tanay.githubrepoviewer.model.presistent.CommitEntity;
import com.tandon.tanay.githubrepoviewer.model.presistent.CommitEntityDao;
import com.tandon.tanay.githubrepoviewer.model.presistent.DaoSession;
import com.tandon.tanay.githubrepoviewer.model.presistent.RepoEntity;
import com.tandon.tanay.githubrepoviewer.model.presistent.RepoEntityDao;

import java.util.List;

import io.reactivex.Observable;

public class DatabaseManager {

    private DaoSession daoSession;

    public DatabaseManager(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public List<CommitEntity> getCommitEntityList(Integer offset, String repoName, String ownerName) {
        CommitEntityDao commitEntityDao = daoSession.getCommitEntityDao();
        return commitEntityDao.queryBuilder()
                .orderDesc(CommitEntityDao.Properties.Timestamp)
                .where(CommitEntityDao.Properties.OwnerName.eq(ownerName),
                        CommitEntityDao.Properties.RepoName.eq(repoName)).list();
    }

    public void saveCommits(List<CommitEntity> commitEntities) {
        CommitEntityDao commitEntityDao = daoSession.getCommitEntityDao();
        commitEntityDao.insertOrReplaceInTx(commitEntities);
    }

    public List<RepoEntity> getRepoEntityList() {
        RepoEntityDao repoEntityDao = daoSession.getRepoEntityDao();
        return repoEntityDao.queryBuilder().where(RepoEntityDao.Properties.Active.eq(true)).list();
    }

    public List<RepoEntity> getRepos(String repoOwner, String repoName) {
        RepoEntityDao repoEntityDao = daoSession.getRepoEntityDao();
        return repoEntityDao.queryBuilder().where(RepoEntityDao.Properties.RepoName.eq(repoName),
                RepoEntityDao.Properties.RepoOwner.eq(repoOwner)).list();
    }

    public Long insertOrReplaceRepoEntity(RepoEntity repoEntity) {
        RepoEntityDao repoEntityDao = daoSession.getRepoEntityDao();
        return repoEntityDao.insertOrReplace(repoEntity);
    }
}
