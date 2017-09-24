package com.tandon.tanay.githubrepoviewer.ui.base;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tandon.tanay.githubrepoviewer.RepoViewer;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private Snackbar snackbar;
    private RepoViewer repoViewer;
    public static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    public void showErrorMessage(View rootView, int messageResId) {
        if (snackbar != null) {
            snackbar.dismiss();
        }
        if (rootView != null) {
            snackbar = Snackbar.make(rootView, messageResId, Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    public void dismissSnackbar() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }


    public RepoViewer getApp() {
        if (repoViewer == null) {
            repoViewer = (RepoViewer) getApplicationContext();
        }
        return repoViewer;
    }

    public void showDialog(String message) {
        if (message != null && message.length() > 0) {
            MessageDialog messageDialog = MessageDialog.newInstance(message);
            messageDialog.show(getSupportFragmentManager(), MessageDialog.TAG);
        }
    }

}
