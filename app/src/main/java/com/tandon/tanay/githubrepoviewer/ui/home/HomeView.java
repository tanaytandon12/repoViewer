package com.tandon.tanay.githubrepoviewer.ui.home;

import com.tandon.tanay.githubrepoviewer.model.view.Commit;
import com.tandon.tanay.githubrepoviewer.ui.base.BaseView;

import java.util.List;
import java.util.Set;


public interface HomeView extends BaseView {

    void commitsLoaded();

    void apiError();

    void refreshStart();

    void refreshEnd();

    void repoInfoLoaded(String repoName, String ownerName);

    void repoDoesNotExist();
}
