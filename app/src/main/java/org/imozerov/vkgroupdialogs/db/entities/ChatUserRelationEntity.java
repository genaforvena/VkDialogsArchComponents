package org.imozerov.vkgroupdialogs.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "chat_to_user", foreignKeys = {
        @ForeignKey(
                entity = ChatEntity.class,
                parentColumns = "id",
                childColumns = "chatId"
        ),
        @ForeignKey(
                entity = UserEntity.class,
                parentColumns = "id",
                childColumns = "userId"
        )
})
public class ChatUserRelationEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long userId;
    private long chatId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
