package com.tandon.tanay.githubrepoviewer.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tandon.tanay.githubrepoviewer.R;
import com.tandon.tanay.githubrepoviewer.constants.IntentFilters;
import com.tandon.tanay.githubrepoviewer.constants.IntentKeys;
import com.tandon.tanay.githubrepoviewer.model.view.Commit;
import com.tandon.tanay.githubrepoviewer.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tandon.tanay.githubrepoviewer.ui.base.BaseActivity.TAG;


public class HomeFragment extends BaseFragment implements HomeView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rootView)
    protected View rootView;

    @BindView(R.id.commitList)
    protected RecyclerView commitListRecyclerView;

    @BindView(R.id.text)
    protected View text;

    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.repoInfo)
    protected TextView repoInfoTextView;

    private HomePresenter homePresenter;
    private CommitAdapter commitAdapter;
    private List<Commit> commits;

    private BroadcastReceiver inputBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            newInput(intent.getStringExtra(IntentKeys.REPO_NAME),
                    intent.getStringExtra(IntentKeys.REPO_OWNER));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commits = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        homePresenter = new HomePresenter();
        homePresenter.init(this, commits);

        init();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(inputBroadcast,
                new IntentFilter(IntentFilters.INPUT_ENTERED));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(inputBroadcast);
    }

    private void init() {
        initCommitList();
        homePresenter.load();
    }

    private void initCommitList() {
        commitAdapter = new CommitAdapter(getContext(), commits);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commitListRecyclerView.setLayoutManager(linearLayoutManager);
        commitListRecyclerView.setHasFixedSize(true);
        commitListRecyclerView.setAdapter(commitAdapter);
    }

    private void newInput(String repoName, String ownerName) {
        homePresenter.newInputDataAdded(repoName, ownerName);
        String text = ownerName + "/" + repoName;
        repoInfoTextView.setText(text);
    }

    @Override
    public void commitsLoaded() {
        if (commits.size() > 0) {
            text.setVisibility(View.GONE);
        }
        commitAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh() {
        homePresenter.refresh();
    }

    @Override
    public void apiError() {
        showErrorMessage(rootView, R.string.somethingWentWrong);
    }

    @Override
    public void refreshStart() {
        Log.d(TAG, "refreshStart: loading data");
    }

    @Override
    public void refreshEnd() {
        swipeRefreshLayout.setRefreshing(false);
        commitAdapter.notifyDataSetChanged();
    }

    @Override
    public void repoInfoLoaded(String repoName, String ownerName) {
        String text = ownerName + "/" + repoName;
        repoInfoTextView.setText(text);
    }
}
