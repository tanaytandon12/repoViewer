package com.tandon.tanay.githubrepoviewer.ui.input;


import com.tandon.tanay.githubrepoviewer.Router;
import com.tandon.tanay.githubrepoviewer.ui.base.BasePresenter;
import com.tandon.tanay.githubrepoviewer.util.TextUtils;

public class InputPresenter extends BasePresenter<InputView> {

    private InputView inputView;

    public void init(InputView inputView) {
        this.inputView = inputView;
    }

    public void inputEntered(String repoName, String ownerName) {
        if (TextUtils.INSTANCE.isValidInput(repoName) && TextUtils.INSTANCE.isValidInput(ownerName)) {
            Router.INSTANCE.sendInputBroadcast(inputView.getViewContext(), repoName, ownerName);
            inputView.correctInput(repoName, ownerName);
        } else if (TextUtils.INSTANCE.isValidInput(repoName)) {
            inputView.incorrectOwnerName();
        } else {
            inputView.incorrectRepoName();
        }
    }

}
