package com.tandon.tanay.githubrepoviewer.data;

import com.tandon.tanay.githubrepoviewer.RepoViewer;
import com.tandon.tanay.githubrepoviewer.data.local.DatabaseManager;
import com.tandon.tanay.githubrepoviewer.data.remote.ApiService;
import com.tandon.tanay.githubrepoviewer.model.api.CommitResponse;
import com.tandon.tanay.githubrepoviewer.model.presistent.CommitEntity;
import com.tandon.tanay.githubrepoviewer.model.presistent.RepoEntity;

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


    public Observable<List<CommitEntity>> getCommitsFromDb(final Integer offset, final String repoName,
                                                           final String ownerName) {
        return Observable.fromCallable(new Callable<List<CommitEntity>>() {
            @Override
            public List<CommitEntity> call() throws Exception {
                return databaseManager.getCommitEntityList(offset, repoName, ownerName);
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
                return databaseManager.getRepoEntityList();
            }
        });
    }

    public Observable<List<CommitResponse>> getCommitsFromApi(final Integer pageNumber,
                                                              final String repoName,
                                                              final String ownerName) {
        return apiService.getCommits(ownerName, repoName, pageNumber);
    }


}
