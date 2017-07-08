package org.imozerov.vkgroupdialogs.persistance.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import org.imozerov.vkgroupdialogs.persistance.db.entities.MessageEntity;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages where chatId = :chatId")
    LiveData<List<MessageEntity>> loadMessages(int chatId);
}
