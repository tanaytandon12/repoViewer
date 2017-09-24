package com.tandon.tanay.githubrepoviewer.data.local;


import com.tandon.tanay.githubrepoviewer.constants.DbConfig;
import com.tandon.tanay.githubrepoviewer.model.presistent.CommitEntity;
import com.tandon.tanay.githubrepoviewer.model.presistent.CommitEntityDao;
import com.tandon.tanay.githubrepoviewer.model.presistent.DaoSession;
import com.tandon.tanay.githubrepoviewer.model.presistent.RepoEntity;
import com.tandon.tanay.githubrepoviewer.model.presistent.RepoEntityDao;

import java.util.List;

public class DatabaseManager {

    private DaoSession daoSession;

    public DatabaseManager(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public List<CommitEntity> getCommitEntityList(Integer offset, String repoName, String ownerName) {
        CommitEntityDao commitEntityDao = daoSession.getCommitEntityDao();
        return commitEntityDao.queryBuilder().limit(DbConfig.LIMIT).offset(offset)
                .orderDesc(CommitEntityDao.Properties.Timestamp).list();
    }

    public void saveCommits(List<CommitEntity> commitEntities) {
        CommitEntityDao commitEntityDao = daoSession.getCommitEntityDao();
        commitEntityDao.insertOrReplaceInTx(commitEntities);
    }

    public List<RepoEntity> getRepoEntityList() {
        RepoEntityDao repoEntityDao = daoSession.getRepoEntityDao();
        return repoEntityDao.queryBuilder().where(RepoEntityDao.Properties.Active.eq(true)).list();
    }
}
