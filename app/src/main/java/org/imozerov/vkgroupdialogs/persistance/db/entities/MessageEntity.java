package org.imozerov.vkgroupdialogs.persistance.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.imozerov.vkgroupdialogs.persistance.model.Message;

@Entity(tableName = "messages", foreignKeys = {
        @ForeignKey(entity = ChatEntity.class,
                parentColumns = "id",
                childColumns = "chatId",
                onDelete = ForeignKey.CASCADE)}, indices = {
        @Index(value = "chatId")
})
public class MessageEntity implements Message {
    @PrimaryKey
    private int id;

    private String text;

    private int chatId;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    // Required empty constructor
    public MessageEntity() {
    }

    public MessageEntity(Message message) {
        id = message.getId();
        text = message.getText();
        this.chatId = message.getChatId();
    }
}
