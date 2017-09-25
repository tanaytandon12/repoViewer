package com.tandon.tanay.githubrepoviewer.data.remote;

import com.tandon.tanay.githubrepoviewer.constants.ApiConfig;
import com.tandon.tanay.githubrepoviewer.model.api.CommitResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    @GET(ApiConfig.Urls.REPO_COMMIT)
    Observable<List<CommitResponse>> getCommits(@Path(ApiConfig.Parameters.OWNER) String ownerId,
                                                @Path(ApiConfig.Parameters.REPO_NAME) String repositoryName,
                                                @Query(ApiConfig.Parameters.PAGE) Integer pageNo);

    @GET(ApiConfig.Urls.REPO)
    Observable<Object> verifyRepository(@Path(ApiConfig.Parameters.OWNER) String ownerId,
                                        @Path(ApiConfig.Parameters.REPO_NAME) String repoName);
}
