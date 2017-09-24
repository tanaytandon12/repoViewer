package com.tandon.tanay.githubrepoviewer.data.local;

import android.content.Context;

import com.tandon.tanay.githubrepoviewer.constants.DbConfig;
import com.tandon.tanay.githubrepoviewer.model.presistent.DaoMaster;


public class DatabaseHelper extends DaoMaster.OpenHelper {

    public DatabaseHelper(Context context, String name) {
        super(context, DbConfig.NAME);
    }
}
