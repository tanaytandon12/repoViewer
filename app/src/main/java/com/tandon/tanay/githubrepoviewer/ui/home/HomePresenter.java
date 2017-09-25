package com.tandon.tanay.githubrepoviewer.ui.home;

import com.tandon.tanay.githubrepoviewer.RepoViewer;
import com.tandon.tanay.githubrepoviewer.constants.DbConfig;
import com.tandon.tanay.githubrepoviewer.data.Repository;
import com.tandon.tanay.githubrepoviewer.model.api.CommitResponse;
import com.tandon.tanay.githubrepoviewer.model.presistent.CommitEntity;
import com.tandon.tanay.githubrepoviewer.model.presistent.RepoEntity;
import com.tandon.tanay.githubrepoviewer.model.view.Commit;
import com.tandon.tanay.githubrepoviewer.ui.base.BasePresenter;
import com.tandon.tanay.githubrepoviewer.util.EntityMapper;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class HomePresenter extends BasePresenter<HomeView> {

    private HomeView homeView;
    private Repository repository;
    private RepoViewer repoViewer;
    private List<Commit> commits;
    private Set<String> commitShaSet;
    private Integer offset;
    private RepoEntity repoEntity;


    private static final String TAG = HomePresenter.class.getSimpleName();

    public void init(HomeView homeView, List<Commit> commits) {
        this.homeView = homeView;
        this.repoViewer = (RepoViewer) homeView.getViewContext().getApplicationContext();
        this.repository = Repository.getInstance(repoViewer);
        this.commits = commits;
        this.commitShaSet = new HashSet<>();
        this.offset = 0;
    }

    public void load() {
        loadActiveRepo();
    }

    private void loadActiveRepo() {
        repository.getActiveRepo().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RepoEntity>>() {
                    @Override
                    public void accept(List<RepoEntity> repoEntities) throws Exception {
                        if (repoEntities != null && repoEntities.size() > 0) {
                            repoEntity = repoEntities.get(0);
                            repoEntityInitialized();
                        } else {
                            insertRepoEntity(EntityMapper.INSTANCE.createNewRepoEntity("rails", "rails"));
                        }
                    }
                });
    }

    private void loadCommitsFromDb() {
        repository.getCommitsFromDb(offset, repoEntity)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CommitEntity>>() {
                    @Override
                    public void accept(List<CommitEntity> commitEntities) throws Exception {
                        addNewCommits(EntityMapper.INSTANCE.getCommitListFromEntityList(commitEntities));
                        homeView.commitsLoaded();
                    }
                });
    }

    private void loadCommitsFromApi() {
        repository.getCommitsFromApi((commits.size() / DbConfig.LIMIT) + 1, repoEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CommitResponse>>() {
                    @Override
                    public void accept(List<CommitResponse> commitResponses) throws Exception {
                        addNewCommits(EntityMapper.INSTANCE.getCommitListFromResponse(commitResponses,
                                repoEntity.getRepoOwner(), repoEntity.getRepoName()));
                        saveCommitsToDb(commitResponses);
                        homeView.commitsLoaded();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        homeView.apiError();
                        throwable.printStackTrace();
                    }
                });
    }

    private void saveCommitsToDb(List<CommitResponse> commitResponses) {
        repository.insertCommits(EntityMapper.INSTANCE.
                getEntitiesFromResponse(commitResponses, repoEntity.getRepoName(), repoEntity.getRepoOwner()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CommitEntity>>() {
                    @Override
                    public void accept(List<CommitEntity> commitEntities) throws Exception {

                    }
                });

    }

    private void sortCommits() {
        Collections.sort(commits, new Comparator<Commit>() {
            @Override
            public int compare(Commit commit, Commit t1) {
                return (int) (t1.commitDateTime.getMillis() - commit.commitDateTime.getMillis());
            }
        });
    }

    private void addNewCommits(List<Commit> commits) {
        for (Commit commit : commits) {
            if (!commitShaSet.contains(commit.sha)) {
                this.commits.add(commit);
                commitShaSet.add(commit.sha);
            }
        }
        sortCommits();
    }

    public void refresh() {
        homeView.refreshStart();
        repository.getCommitsFromApi(1, repoEntity).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CommitResponse>>() {
                    @Override
                    public void accept(List<CommitResponse> commitResponses) throws Exception {
                        addNewCommits(EntityMapper.INSTANCE
                                .getCommitListFromResponse(commitResponses,
                                        repoEntity.getRepoOwner(), repoEntity.getRepoName()));
                        saveCommitsToDb(commitResponses);
                        homeView.refreshEnd();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        homeView.apiError();
                        homeView.refreshEnd();
                    }
                });
    }

    public void newInputDataAdded(String repoName, String ownerName) {
        if (repoEntity == null || !repoEntity.getRepoName().equals(repoName)
                || !repoEntity.getRepoOwner().equals(ownerName)) {
            verifyRepo(ownerName, repoName);
        }
    }

    private void verifyRepo(final String ownerName, final String repoName) {
        repository.verifyApi(ownerName, repoName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        getRepoEntity(ownerName, repoName);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        homeView.repoDoesNotExist();
                    }
                });
    }

    private void getRepoEntity(final String ownerName, final String repoName) {
        repository.getRepoEntity(repoName, ownerName, repoEntity).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RepoEntity>() {
                    @Override
                    public void accept(RepoEntity repoEntity) throws Exception {
                        if (repoEntity != null && repoEntity.getId() != null) {
                            HomePresenter.this.repoEntity = repoEntity;
                            repoEntityInitialized();
                        } else {
                            insertRepoEntity(repoEntity);
                        }
                    }
                });
    }

    private void insertRepoEntity(final RepoEntity repoEntity) {
        repository.insertRepoEntity(repoEntity, this.repoEntity).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        repoEntity.setId(aLong);
                        HomePresenter.this.repoEntity = repoEntity;
                        repoEntityInitialized();
                    }
                });
    }

    private void repoEntityInitialized() {
        homeView.repoInfoLoaded(repoEntity.getRepoName(), repoEntity.getRepoOwner());
        this.commits.clear();
        loadCommitsFromDb();
        loadCommitsFromApi();
    }

}
