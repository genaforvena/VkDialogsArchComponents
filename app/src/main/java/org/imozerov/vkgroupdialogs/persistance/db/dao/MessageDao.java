package org.imozerov.vkgroupdialogs.persistance.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.imozerov.vkgroupdialogs.vo.Message;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages where chatId = :chatId")
    LiveData<List<Message>> loadMessages(int chatId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Message> messages);
}
