package com.tandon.tanay.githubrepoviewer.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tandon.tanay.githubrepoviewer.R;
import com.tandon.tanay.githubrepoviewer.constants.IntentKeys;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MessageDialog extends BaseDialogFragment implements View.OnClickListener {

    public static final String TAG = MessageDialog.class.getSimpleName();

    @BindView(R.id.message)
    protected TextView messageText;

    @BindView(R.id.okayBtn)
    protected View okayButton;

    private String message;

    public static MessageDialog newInstance(String message) {
        MessageDialog messageDialog = new MessageDialog();
        Bundle args = new Bundle();
        args.putString(IntentKeys.MESSAGE, message);
        messageDialog.setArguments(args);
        return messageDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        message = args.getString(IntentKeys.MESSAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_message, container, false);

        ButterKnife.bind(this, view);

        okayButton.setOnClickListener(this);
        messageText.setText(message);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.okayBtn: {
                dismissDialog();
                break;
            }
        }
    }
}
