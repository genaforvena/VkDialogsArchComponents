package org.imozerov.vkgroupdialogs.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import org.imozerov.vkgroupdialogs.db.converters.DateConverter;
import org.imozerov.vkgroupdialogs.db.dao.ChatDao;
import org.imozerov.vkgroupdialogs.db.dao.MessageDao;
import org.imozerov.vkgroupdialogs.vo.Chat;
import org.imozerov.vkgroupdialogs.vo.ChatUserRelation;
import org.imozerov.vkgroupdialogs.vo.Message;
import org.imozerov.vkgroupdialogs.vo.User;

@Database(entities = {Chat.class, Message.class, User.class, ChatUserRelation.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "imozerov-vk-chat-db";

    public abstract ChatDao chatDao();

    public abstract MessageDao messageDao();
}
