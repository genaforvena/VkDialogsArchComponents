package org.imozerov.vkgroupdialogs.di;


import android.app.Application;
import android.arch.persistence.room.Room;

import org.imozerov.vkgroupdialogs.db.AppDatabase;
import org.imozerov.vkgroupdialogs.db.dao.ChatDao;
import org.imozerov.vkgroupdialogs.db.dao.MessageDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton
    @Provides
    AppDatabase provideDb(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, AppDatabase.DATABASE_NAME).build();
    }

    @Singleton @Provides
    ChatDao provideUserDao(AppDatabase db) {
        return db.chatDao();
    }

    @Singleton @Provides
    MessageDao provideRepoDao(AppDatabase db) {
        return db.messageDao();
    }
}
