package org.imozerov.vkgroupdialogs.persistance.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import org.imozerov.vkgroupdialogs.persistance.db.entities.ChatEntity;

import java.util.List;

@Dao
public interface ChatDao {
    @Query("SELECT * FROM chats")
    LiveData<List<ChatEntity>> loadChats();
}
