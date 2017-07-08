package org.imozerov.vkgroupdialogs.persistance.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import org.imozerov.vkgroupdialogs.persistance.db.converters.DateConverter;
import org.imozerov.vkgroupdialogs.persistance.db.dao.ChatDao;
import org.imozerov.vkgroupdialogs.persistance.db.dao.MessageDao;
import org.imozerov.vkgroupdialogs.persistance.db.entities.ChatEntity;
import org.imozerov.vkgroupdialogs.persistance.db.entities.MessageEntity;

@Database(entities = {ChatEntity.class, MessageEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    static final String DATABASE_NAME = "imozerov-vk-chat-db";

    public abstract ChatDao chatDao();

    public abstract MessageDao messageDao();
}
