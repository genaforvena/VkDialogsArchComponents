package org.imozerov.vkgroupdialogs.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import org.imozerov.vkgroupdialogs.db.entities.ChatUserRelationEntity;

import java.util.List;

@Dao
public interface ChatUserRelationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ChatUserRelationEntity> chatUserRelationEntities);
}
