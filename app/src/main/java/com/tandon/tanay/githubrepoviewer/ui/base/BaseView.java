package com.tandon.tanay.githubrepoviewer.ui.base;

import android.content.Context;
import android.view.View;

/**
 * Created by tanaytandon on 20/09/17.
 */

public interface BaseView {

    Context getViewContext();

    void showErrorMessage(View rootView, int messageResId);
}
