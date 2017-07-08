package org.imozerov.vkgroupdialogs.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "messages", foreignKeys = {
        @ForeignKey(entity = Chat.class,
                parentColumns = "id",
                childColumns = "chatId",
                onDelete = ForeignKey.CASCADE)}, indices = {
        @Index(value = "chatId")
})
public class Message {
    @PrimaryKey
    private int id;
    private String text;
    private int chatId;
    private int senderId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}
