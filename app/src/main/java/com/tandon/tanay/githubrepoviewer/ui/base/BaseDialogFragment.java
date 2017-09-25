package com.tandon.tanay.githubrepoviewer.ui.base;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.View;


public abstract class BaseDialogFragment extends DialogFragment implements BaseView {

    private Snackbar snackbar;

    protected void dismissDialog() {
        if (getDialog() != null) {
            getDialog().dismiss();
        }
    }

    public void dismissSnackbar() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }


    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void showMessage(View rootView, int messageResId) {
        if (snackbar != null) {
            snackbar.dismiss();
        }
        if (rootView != null) {
            snackbar = Snackbar.make(rootView, messageResId, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    @Override
    public void hideMessage() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }
}
