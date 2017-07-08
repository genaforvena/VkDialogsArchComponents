package org.imozerov.vkgroupdialogs.persistance.db;

import org.imozerov.vkgroupdialogs.persistance.db.entities.ChatEntity;
import org.imozerov.vkgroupdialogs.persistance.db.entities.MessageEntity;

import java.util.ArrayList;
import java.util.List;

class DatabaseInitUtil {
    static void initializeDb(AppDatabase db) {
        List<ChatEntity> chats = new ArrayList<>();

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(1);

        ChatEntity chatEntity2 = new ChatEntity();
        chatEntity2.setId(2);

        chats.add(chatEntity);
        chats.add(chatEntity2);

        List<MessageEntity> messages = new ArrayList<>();

        MessageEntity message = new MessageEntity();
        message.setId(1);
        message.setChatId(1);
        message.setText("dfsdf");

        messages.add(message);

        db.beginTransaction();
        try {
            db.chatDao().insertAll(chats);
            db.messageDao().insertAll(messages);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}
