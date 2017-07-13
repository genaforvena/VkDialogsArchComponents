package org.imozerov.vkgroupdialogs.di;


import android.app.Application;
import android.arch.persistence.room.Room;

import org.imozerov.vkgroupdialogs.db.AppDatabase;
import org.imozerov.vkgroupdialogs.db.dao.ChatDao;
import org.imozerov.vkgroupdialogs.db.dao.ChatUserRelationDao;
import org.imozerov.vkgroupdialogs.db.dao.MessageDao;
import org.imozerov.vkgroupdialogs.db.dao.UserDao;

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
    ChatDao provideChatDao(AppDatabase db) {
        return db.chatDao();
    }

    @Singleton @Provides
    MessageDao provideMessageDao(AppDatabase db) {
        return db.messageDao();
    }

    @Singleton @Provides
    UserDao provideUserDao(AppDatabase db) {
        return db.userDao();
    }

    @Singleton @Provides
    ChatUserRelationDao provideChatUserRelationDao(AppDatabase db) {
        return db.chatUserRelationDao();
    }
}
