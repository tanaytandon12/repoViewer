package com.tandon.tanay.githubrepoviewer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.tandon.tanay.githubrepoviewer.constants.IntentFilters;
import com.tandon.tanay.githubrepoviewer.constants.IntentKeys;

public enum Router {

    INSTANCE;

    public void sendInputBroadcast(Context context, String repoName, String ownerName) {
        Intent intent = new Intent(IntentFilters.INPUT_ENTERED);
        intent.putExtra(IntentKeys.REPO_NAME, repoName);
        intent.putExtra(IntentKeys.REPO_OWNER, ownerName);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
