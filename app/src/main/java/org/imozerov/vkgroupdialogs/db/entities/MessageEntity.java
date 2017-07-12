package org.imozerov.vkgroupdialogs.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import javax.annotation.Nullable;

@Entity(tableName = "messages",
        indices = {@Index("id")},
        foreignKeys = {
                @ForeignKey(entity = ChatEntity.class,
                        parentColumns = "id",
                        childColumns = "chatId",
                        onDelete = ForeignKey.CASCADE)
        })
public class MessageEntity {
    @PrimaryKey
    private long id;
    private String text;
    @Nullable
    private String photo;
    private Date date;
    private long chatId;
    private long senderId;
    private boolean isMine;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    @Nullable
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(@Nullable String photo) {
        this.photo = photo;
    }
}
