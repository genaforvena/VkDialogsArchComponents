package org.imozerov.vkgroupdialogs.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.imozerov.vkgroupdialogs.db.entities.ChatCollageEntity;
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity;
import org.imozerov.vkgroupdialogs.db.model.Chat;
import org.imozerov.vkgroupdialogs.db.model.ChatInfo;

import java.util.List;

@Dao
public interface ChatDao {
    @Query("SELECT chats.*, collages.collage FROM chats LEFT JOIN collages ON chats.id = collages.id")
    LiveData<List<Chat>> loadChats();

    @Query("SELECT chats.id, chats.name, collages.collage, chats.photo, chats.usersCount " +
            "FROM chats JOIN collages ON chats.id = collages.id " +
            "WHERE chats.id = :chatId")
    LiveData<ChatInfo> loadChatInfo(long chatId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ChatEntity> chats);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCollage(ChatCollageEntity collageEntity);
}
