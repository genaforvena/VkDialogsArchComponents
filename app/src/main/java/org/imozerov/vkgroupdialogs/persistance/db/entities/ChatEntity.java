package org.imozerov.vkgroupdialogs.persistance.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.imozerov.vkgroupdialogs.persistance.model.Chat;

@Entity(tableName = "chats")
public class ChatEntity implements Chat {
    @PrimaryKey
    private int id;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Required empty constructor
    public ChatEntity() {
    }

    public ChatEntity(Chat chat) {
        id = chat.getId();
    }
}
