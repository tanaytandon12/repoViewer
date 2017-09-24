package com.tandon.tanay.githubrepoviewer;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tandon.tanay.githubrepoviewer.constants.ApiConfig;
import com.tandon.tanay.githubrepoviewer.constants.DbConfig;
import com.tandon.tanay.githubrepoviewer.data.local.DatabaseHelper;
import com.tandon.tanay.githubrepoviewer.data.remote.ApiService;
import com.tandon.tanay.githubrepoviewer.model.presistent.DaoMaster;
import com.tandon.tanay.githubrepoviewer.model.presistent.DaoSession;
import com.tandon.tanay.githubrepoviewer.util.DatetimeSerializer;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepoViewer extends Application {

    private DaoSession daoSession;
    private ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        setUpGreendao();
        setUpRetrofit();
    }

    private void setUpGreendao() {
        DatabaseHelper helper = new DatabaseHelper(this, DbConfig.NAME);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    private void setUpRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        okHttpClient.addInterceptor(httpLoggingInterceptor);

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(DateTime.class, new DatetimeSerializer()).create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConfig.Urls.BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        apiService = retrofit.create(ApiService.class);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public ApiService getApiService() {
        return apiService;
    }


}
