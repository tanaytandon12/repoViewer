package com.tandon.tanay.githubrepoviewer.constants;

/**
 * Created by tanaytandon on 22/09/17.
 */

public interface ApiConfig {

    interface Urls {

        String BASE_URL = "https://api.github.com/";


        String REPO_COMMIT = "repos/{owner}/{repoName}/commits";

        String REPO = "repos/{owner}/{repoName}";
    }

    interface Parameters {

        String PAGE = "page";
        String OWNER = "owner";
        String REPO_NAME = "repoName";
    }
}
