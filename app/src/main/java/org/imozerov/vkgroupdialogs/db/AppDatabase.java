package org.imozerov.vkgroupdialogs.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import org.imozerov.vkgroupdialogs.db.converters.DateConverter;
import org.imozerov.vkgroupdialogs.db.dao.ChatDao;
import org.imozerov.vkgroupdialogs.db.dao.MessageDao;
import org.imozerov.vkgroupdialogs.db.dao.UserDao;
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity;
import org.imozerov.vkgroupdialogs.db.entities.ChatUserRelationEntity;
import org.imozerov.vkgroupdialogs.db.entities.MessageEntity;
import org.imozerov.vkgroupdialogs.db.entities.UserEntity;

@Database(entities = {ChatEntity.class, MessageEntity.class, UserEntity.class, ChatUserRelationEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "imozerov-vk-chat-db";

    public abstract ChatDao chatDao();

    public abstract MessageDao messageDao();

    public abstract UserDao userDao();
}
