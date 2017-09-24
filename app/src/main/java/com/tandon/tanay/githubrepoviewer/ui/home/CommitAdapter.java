package com.tandon.tanay.githubrepoviewer.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tandon.tanay.githubrepoviewer.R;
import com.tandon.tanay.githubrepoviewer.model.view.Commit;

import java.util.List;


public class CommitAdapter extends RecyclerView.Adapter<CommitViewHolder> {

    private List<Commit> commitList;

    private Context context;
    private LayoutInflater layoutInflater;

    public CommitAdapter(Context context, List<Commit> commits) {
        this.context = context;
        this.commitList = commits;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CommitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommitViewHolder(layoutInflater.inflate(R.layout.item_commit, parent, false));
    }

    @Override
    public void onBindViewHolder(CommitViewHolder holder, int position) {
        holder.bind(context, commitList.get(position));
    }

    @Override
    public int getItemCount() {
        return commitList.size();
    }
}
