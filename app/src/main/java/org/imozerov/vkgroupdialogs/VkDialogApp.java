package org.imozerov.vkgroupdialogs;

import android.app.Activity;
import android.app.Application;
import android.database.DatabaseUtils;
import android.os.AsyncTask;

import org.imozerov.vkgroupdialogs.db.AppDatabase;
import org.imozerov.vkgroupdialogs.db.DatabaseInitUtil;
import org.imozerov.vkgroupdialogs.di.AppInjector;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class VkDialogApp extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    @Inject
    AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjector.init(this);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseInitUtil.initializeDb(appDatabase);
                return null;
            }
        }.execute();
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
