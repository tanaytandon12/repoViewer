package com.tandon.tanay.githubrepoviewer.ui.base;

import android.content.Context;
import android.view.View;

/**
 * Created by tanaytandon on 20/09/17.
 */

public interface BaseView {

    Context getViewContext();

    void showMessage(View rootView, int messageResId);

    void hideMessage();
}
