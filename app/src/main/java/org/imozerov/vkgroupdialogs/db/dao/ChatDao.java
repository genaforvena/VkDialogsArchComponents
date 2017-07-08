package org.imozerov.vkgroupdialogs.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.imozerov.vkgroupdialogs.vo.Chat;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ChatDao {
    @Query("SELECT * FROM chats")
    Flowable<List<Chat>> loadChats();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Chat> chats);

    @Query("SELECT * FROM chats where id = :chatId")
    Flowable<Chat> loadChat(int chatId);
}
