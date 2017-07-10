package org.imozerov.vkgroupdialogs.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.imozerov.vkgroupdialogs.db.entities.MessageEntity;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages where chatId = :chatId")
    Flowable<List<MessageEntity>> loadMessages(long chatId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MessageEntity> messages);
}
