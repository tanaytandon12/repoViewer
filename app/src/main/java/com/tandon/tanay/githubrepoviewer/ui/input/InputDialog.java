package com.tandon.tanay.githubrepoviewer.ui.input;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tandon.tanay.githubrepoviewer.R;
import com.tandon.tanay.githubrepoviewer.constants.IntentKeys;
import com.tandon.tanay.githubrepoviewer.ui.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputDialog extends BaseDialogFragment implements View.OnClickListener, InputView {

    public static final String TAG = InputDialog.class.getSimpleName();

    @BindView(R.id.repoNameEdit)
    protected EditText repoNameInput;

    @BindView(R.id.ownerNameEdit)
    protected EditText ownerNameInput;

    @BindView(R.id.okayBtn)
    protected View okayButton;

    @BindView(R.id.rootView)
    protected View rootView;

    private String ownerName, repoName;

    private InputPresenter inputPresenter;

    public static InputDialog newInstance(String ownerName, String repoName) {
        InputDialog inputDialog = new InputDialog();
        Bundle args = new Bundle();
        args.putString(IntentKeys.REPO_NAME, repoName);
        args.putString(IntentKeys.REPO_OWNER, ownerName);
        inputDialog.setArguments(args);
        return inputDialog;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        ownerName = args.getString(IntentKeys.REPO_OWNER);
        repoName = args.getString(IntentKeys.REPO_NAME);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_input, container, false);

        ButterKnife.bind(this, view);

        inputPresenter = new InputPresenter();
        inputPresenter.init(this);

        okayButton.setOnClickListener(this);
        repoNameInput.setText(repoName);
        ownerNameInput.setText(ownerName);

        return view;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.okayBtn: {
                okayButtonClicked();
                break;
            }
        }
    }

    private void okayButtonClicked() {
        inputPresenter.inputEntered(repoNameInput.getText().toString(),
                ownerNameInput.getText().toString());
    }

    @Override
    public void incorrectRepoName() {
        showErrorMessage(rootView, R.string.incorrectRepoName);
        repoNameInput.requestFocus();
    }

    @Override
    public void incorrectOwnerName() {
        showErrorMessage(rootView, R.string.incorrectOwnerName);
        ownerNameInput.requestFocus();
    }

    @Override
    public void correctInput(String repoName, String ownerName) {
        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra(IntentKeys.REPO_OWNER, ownerName);
            intent.putExtra(IntentKeys.REPO_NAME, repoName);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
        dismissDialog();
    }

}
