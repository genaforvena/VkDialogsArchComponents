package org.imozerov.vkgroupdialogs.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.imozerov.vkgroupdialogs.db.entities.ChatEntity;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ChatDao {
    @Query("SELECT * FROM chats")
    Flowable<List<ChatEntity>> loadChats();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ChatEntity> chats);

    @Query("SELECT userId FROM chat_to_user where chatId = :chatId")
    Flowable<List<Integer>> loadChatUserIds(long chatId);
}
