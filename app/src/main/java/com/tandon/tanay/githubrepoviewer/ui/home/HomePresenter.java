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
    String repoName, ownerName;

    private static final String TAG = HomePresenter.class.getSimpleName();

    public void init(HomeView homeView, List<Commit> commits) {
        this.homeView = homeView;
        this.repoViewer = (RepoViewer) homeView.getViewContext().getApplicationContext();
        this.repository = Repository.getInstance(repoViewer);
        this.commits = commits;
        this.commitShaSet = new HashSet<>();
        this.offset = 0;
        repoName = "rails";
        ownerName = "rails";
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
                            RepoEntity repoEntity = repoEntities.get(0);
                            repoName = repoEntity.getRepoName();
                            ownerName = repoEntity.getRepoOwner();
                        }
                        homeView.repoInfoLoaded(repoName, ownerName);
                        loadCommitsFromDb();
                        loadCommitsFromApi();
                    }
                });
    }

    private void loadCommitsFromDb() {
        repository.getCommitsFromDb(offset, repoName, ownerName).subscribeOn(Schedulers.computation())
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
        repository.getCommitsFromApi((commits.size() / DbConfig.LIMIT) + 1, repoName, ownerName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CommitResponse>>() {
                    @Override
                    public void accept(List<CommitResponse> commitResponses) throws Exception {
                        addNewCommits(EntityMapper.INSTANCE.getCommitListFromResponse(commitResponses,
                                ownerName, repoName));
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
                getEntitiesFromResponse(commitResponses, repoName, ownerName))
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
                return (int) (commit.commitDateTime.getMillis() - t1.commitDateTime.getMillis());
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
        repository.getCommitsFromApi(1, repoName, ownerName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CommitResponse>>() {
                    @Override
                    public void accept(List<CommitResponse> commitResponses) throws Exception {
                        addNewCommits(EntityMapper.INSTANCE
                                .getCommitListFromResponse(commitResponses, ownerName, repoName));
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
        this.repoName = repoName;
        this.ownerName = ownerName;
        this.commits.clear();
        refresh();
    }
}
