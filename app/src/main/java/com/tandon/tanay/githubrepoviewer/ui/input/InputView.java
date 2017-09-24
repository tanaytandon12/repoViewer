package com.tandon.tanay.githubrepoviewer.ui.input;

import com.tandon.tanay.githubrepoviewer.ui.base.BaseView;


public interface InputView extends BaseView {

    void incorrectRepoName();

    void incorrectOwnerName();

    void correctInput(String... inputs);
}
