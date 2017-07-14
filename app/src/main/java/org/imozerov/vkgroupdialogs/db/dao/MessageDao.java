package org.imozerov.vkgroupdialogs.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.imozerov.vkgroupdialogs.db.model.Message;
import org.imozerov.vkgroupdialogs.db.entities.MessageEntity;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT messages.*, users.photoUrl " +
            "FROM messages join users ON messages.senderId = users.id " +
            "WHERE messages.chatId = :chatId " +
            "ORDER BY date(messages.date) DESC")
    LiveData<List<Message>> loadMessages(long chatId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MessageEntity> messages);
}
