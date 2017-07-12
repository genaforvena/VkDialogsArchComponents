package org.imozerov.vkgroupdialogs.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Relation;
import android.graphics.Bitmap;

import org.imozerov.vkgroupdialogs.db.entities.ChatUserRelationEntity;

import java.util.List;

import javax.annotation.Nullable;

public class ChatInfo {
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "avatar")
    private Bitmap photoFallback;
    @Nullable
    @ColumnInfo(name = "photo")
    private String photo;
    @ColumnInfo(name = "usersCount")
    private int usersCount;

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

    public Bitmap getPhotoFallback() {
        return photoFallback;
    }

    public void setPhotoFallback(Bitmap photoFallback) {
        this.photoFallback = photoFallback;
    }

    @Nullable
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(@Nullable String photo) {
        this.photo = photo;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }
}
