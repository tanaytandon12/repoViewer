package com.tandon.tanay.githubrepoviewer.ui.base;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.tandon.tanay.githubrepoviewer.RepoViewer;

public abstract class BaseFragment extends Fragment implements BaseView {

    private Snackbar snackbar;
    private RepoViewer repoViewer;

    @Override
    public void showMessage(View rootView, int messageResId) {
        if (snackbar != null) {
            snackbar.dismiss();
        }
        if (rootView != null) {
            snackbar = Snackbar.make(rootView, messageResId, Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }
    }


    @Override
    public void hideMessage() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }


    public void showDialog(String message) {
        if (message != null && message.length() > 0) {
            MessageDialog messageDialog = MessageDialog.newInstance(message);
            messageDialog.show(getChildFragmentManager(), MessageDialog.TAG);
        }
    }


    public RepoViewer getApp() {
        if (repoViewer == null) {
            repoViewer = (RepoViewer) getActivity().getApplicationContext();
        }
        return repoViewer;
    }
}
