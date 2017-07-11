package org.imozerov.vkgroupdialogs.chat;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Relation;
import android.graphics.Bitmap;

import org.imozerov.vkgroupdialogs.db.entities.ChatUserRelationEntity;

import java.util.List;

public class ChatInfo {
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "avatar")
    private Bitmap photo;

    @Relation(
            entity = ChatUserRelationEntity.class,
            parentColumn = "id",
            entityColumn = "chatId"
    )
    private List<ChatUserRelationEntity> userIds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChatUserRelationEntity> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<ChatUserRelationEntity> userIds) {
        this.userIds = userIds;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
