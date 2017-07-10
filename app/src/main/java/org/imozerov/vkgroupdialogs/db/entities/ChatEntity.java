package org.imozerov.vkgroupdialogs.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import java.util.Date;

@Entity(tableName = "chats")
public class ChatEntity {
    @PrimaryKey
    private long id;
    private String name;
    private Bitmap avatar;
    private String lastMessageText;
    private Date lastMessageTime;

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

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
    }

    public Date getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Date lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
