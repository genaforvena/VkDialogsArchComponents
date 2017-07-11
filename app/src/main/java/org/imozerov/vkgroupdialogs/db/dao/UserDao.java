package org.imozerov.vkgroupdialogs.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.imozerov.vkgroupdialogs.db.entities.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM messages where id = :userId")
    LiveData<List<UserEntity>> loadUser(long userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserEntity> users);
}
