package com.tandon.tanay.githubrepoviewer.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tandon.tanay.githubrepoviewer.R;
import com.tandon.tanay.githubrepoviewer.model.view.Commit;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CommitViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.login)
    protected TextView login;

    @BindView(R.id.sha)
    protected TextView sha;

    @BindView(R.id.message)
    protected TextView message;

    @BindView(R.id.committerImg)
    protected ImageView committerImage;

    public CommitViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Context context, Commit commit) {
        login.setText(commit.userLogin);
        sha.setText(commit.sha);
        message.setText(commit.message);
        Glide.with(context).load(commit.avatarUrl).apply(RequestOptions.circleCropTransform())
                .into(committerImage);
    }
}
