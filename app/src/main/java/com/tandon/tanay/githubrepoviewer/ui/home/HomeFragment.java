package com.tandon.tanay.githubrepoviewer.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tandon.tanay.githubrepoviewer.R;
import com.tandon.tanay.githubrepoviewer.constants.IntentKeys;
import com.tandon.tanay.githubrepoviewer.constants.RequestCodes;
import com.tandon.tanay.githubrepoviewer.model.view.Commit;
import com.tandon.tanay.githubrepoviewer.ui.base.BaseFragment;
import com.tandon.tanay.githubrepoviewer.ui.input.InputDialog;
import com.tandon.tanay.githubrepoviewer.util.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tandon.tanay.githubrepoviewer.ui.base.BaseActivity.TAG;


public class HomeFragment extends BaseFragment implements HomeView,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @BindView(R.id.rootView)
    protected View rootView;

    @BindView(R.id.commitList)
    protected RecyclerView commitListRecyclerView;

    @BindView(R.id.text)
    protected View text;

    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.repoInfo)
    protected Button repoInfoButton;

    private HomePresenter homePresenter;
    private CommitAdapter commitAdapter;
    private List<Commit> commits;
    private String repoName, ownerName;

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
        if (!DeviceUtils.INSTANCE.isNetworkConnected(getContext())) {
            showMessage(rootView, R.string.internet_not_connected);
        } else {
            hideMessage();
        }
    }

    private void init() {
        initCommitList();
        repoInfoButton.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
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
        String text = ownerName + "/" + repoName;
        repoInfoButton.setText(text);
        homePresenter.newInputDataAdded(repoName, ownerName);
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
        showMessage(rootView, R.string.somethingWentWrong);
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
        this.ownerName = ownerName;
        this.repoName = repoName;
        String text = ownerName + "/" + repoName;
        repoInfoButton.setText(text);
    }

    @Override
    public void repoDoesNotExist() {
        showMessage(rootView, R.string.repoInfoIncorrect);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.repoInfo: {
                showInputDialog();
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.CORRECT_INPUT_ENTERED: {
                    String repoName = data.getStringExtra(IntentKeys.REPO_NAME);
                    String ownerName = data.getStringExtra(IntentKeys.REPO_OWNER);
                    newInput(repoName, ownerName);
                    break;
                }
            }
        }
    }

    private void showInputDialog() {
        InputDialog inputDialog = InputDialog.newInstance(ownerName, repoName);
        inputDialog.setTargetFragment(this, RequestCodes.CORRECT_INPUT_ENTERED);
        inputDialog.show(getFragmentManager(), InputDialog.TAG);
    }
}
