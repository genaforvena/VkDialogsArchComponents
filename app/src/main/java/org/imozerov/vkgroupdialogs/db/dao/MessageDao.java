package org.imozerov.vkgroupdialogs.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.imozerov.vkgroupdialogs.vo.Message;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages where chatId = :chatId")
    Flowable<List<Message>> loadMessages(int chatId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Message> messages);
}
