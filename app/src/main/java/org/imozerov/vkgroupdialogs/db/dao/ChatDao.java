package org.imozerov.vkgroupdialogs.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.imozerov.vkgroupdialogs.db.model.ChatInfo;
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity;
import org.imozerov.vkgroupdialogs.db.entities.UserEntity;

import java.util.List;

@Dao
public interface ChatDao {
    @Query("SELECT * FROM chats")
    LiveData<List<ChatEntity>> loadChats();

    @Query("SELECT chats.id, chats.name, chats.avatar, chats.photo, chats.usersCount " +
            "FROM chats WHERE id = :chatId")
    LiveData<ChatInfo> loadChatInfo(long chatId);

    @Query("SELECT users.* " +
            "FROM chat_to_user JOIN users ON chat_to_user.userId = users.id " +
            "WHERE chat_to_user.chatId = :chatId")
    LiveData<List<UserEntity>> loadUsers(long chatId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ChatEntity> chats);
}
