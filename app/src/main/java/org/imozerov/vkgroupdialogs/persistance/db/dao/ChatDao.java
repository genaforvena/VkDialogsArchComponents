package org.imozerov.vkgroupdialogs.persistance.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.imozerov.vkgroupdialogs.vo.Chat;

import java.util.List;

@Dao
public interface ChatDao {
    @Query("SELECT * FROM chats")
    LiveData<List<Chat>> loadChats();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Chat> chats);

    @Query("SELECT * FROM chats where id = :chatId")
    LiveData<Chat> loadChat(int chatId);
}
