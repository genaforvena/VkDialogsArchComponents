package org.imozerov.vkgroupdialogs;

import android.app.Activity;
import android.app.Application;

import com.facebook.stetho.Stetho;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import org.imozerov.vkgroupdialogs.db.AppDatabase;
import org.imozerov.vkgroupdialogs.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import kotlin.NotImplementedError;

public class VkDialogApp extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    @Inject
    AppDatabase appDatabase;

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                throw new NotImplementedError("VKAccessToken is invali");
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
        Stetho.initializeWithDefaults(this);

        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
