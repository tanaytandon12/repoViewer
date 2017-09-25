package com.tandon.tanay.githubrepoviewer.data;

import com.tandon.tanay.githubrepoviewer.RepoViewer;
import com.tandon.tanay.githubrepoviewer.data.local.DatabaseManager;
import com.tandon.tanay.githubrepoviewer.data.remote.ApiService;
import com.tandon.tanay.githubrepoviewer.model.api.CommitResponse;
import com.tandon.tanay.githubrepoviewer.model.presistent.CommitEntity;
import com.tandon.tanay.githubrepoviewer.model.presistent.RepoEntity;
import com.tandon.tanay.githubrepoviewer.util.EntityMapper;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class Repository {


    private DatabaseManager databaseManager;
    private ApiService apiService;
    private RepoViewer repoViewer;

    private static Repository repository;

    public static Repository getInstance(RepoViewer repoViewer) {
        if (repository == null) {
            repository = new Repository(repoViewer);
        }
        return repository;
    }

    private Repository(RepoViewer repoViewer) {
        this.repoViewer = repoViewer;
        this.apiService = this.repoViewer.getApiService();
        this.databaseManager = new DatabaseManager(this.repoViewer.getDaoSession());
    }


    public Observable<List<CommitEntity>> getCommitsFromDb(final Integer offset,
                                                           final RepoEntity repoEntity) {
        return Observable.fromCallable(new Callable<List<CommitEntity>>() {
            @Override
            public List<CommitEntity> call() throws Exception {
                return databaseManager.getCommitEntityList(offset, repoEntity.getRepoName(),
                        repoEntity.getRepoOwner());
            }
        });
    }

    public Observable<List<CommitEntity>> insertCommits(final List<CommitEntity> commitEntities) {
        return Observable.fromCallable(new Callable<List<CommitEntity>>() {
            @Override
            public List<CommitEntity> call() throws Exception {
                databaseManager.saveCommits(commitEntities);
                return commitEntities;
            }
        });
    }


    public Observable<List<RepoEntity>> getActiveRepo() {
        return Observable.fromCallable(new Callable<List<RepoEntity>>() {
            @Override
            public List<RepoEntity> call() throws Exception {
                List<RepoEntity> repoEntities = databaseManager.getRepoEntityList();
                return databaseManager.getRepoEntityList();
            }
        });
    }

    public Observable<List<CommitResponse>> getCommitsFromApi(final Integer pageNumber,
                                                              RepoEntity repoEntity) {
        return apiService.getCommits(repoEntity.getRepoOwner(), repoEntity.getRepoName(),
                pageNumber);
    }

    public Observable<Object> verifyApi(String repoOwner, String repoName) {
        return apiService.verifyRepository(repoOwner, repoName);
    }

    public Observable<RepoEntity> getRepoEntity(final String repoName, final String repoOwner,
                                                final RepoEntity repoEntity) {
        return Observable.fromCallable(new Callable<RepoEntity>() {
            @Override
            public RepoEntity call() throws Exception {
                List<RepoEntity> repoEntities = databaseManager.getRepos(repoOwner, repoName);
                if (repoEntities != null && repoEntities.size() > 0) {
                    setRepoEntityToInactive(repoEntity);
                    RepoEntity newRepoEntity = repoEntities.get(0);
                    newRepoEntity.setActive(true);
                    databaseManager.insertOrReplaceRepoEntity(newRepoEntity);
                    return newRepoEntity;
                }
                return EntityMapper.INSTANCE.createNewRepoEntity(repoOwner, repoName);
            }
        });
    }


    public Observable<Long> insertRepoEntity(final RepoEntity repoEntity,
                                             final RepoEntity previousRepoEntity) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                setRepoEntityToInactive(previousRepoEntity);
                return databaseManager.insertOrReplaceRepoEntity(repoEntity);
            }
        });
    }

    private void setRepoEntityToInactive(RepoEntity repoEntity) {
        if (repoEntity != null) {
            repoEntity.setActive(false);
            databaseManager.insertOrReplaceRepoEntity(repoEntity);
        }
    }


}
