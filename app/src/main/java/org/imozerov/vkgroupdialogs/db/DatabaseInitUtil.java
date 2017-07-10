package org.imozerov.vkgroupdialogs.db;

import org.imozerov.vkgroupdialogs.db.entities.ChatEntity;
import org.imozerov.vkgroupdialogs.db.entities.MessageEntity;
import org.imozerov.vkgroupdialogs.db.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInitUtil {
    public static void initializeDb(AppDatabase db) {
        List<ChatEntity> chats = new ArrayList<>();

        ChatEntity chat = new ChatEntity();
        chat.setId(1);

        ChatEntity chat2 = new ChatEntity();
        chat2.setId(2);

        ChatEntity chat3 = new ChatEntity();
        chat3.setId(3);

        ChatEntity chat4 = new ChatEntity();
        chat4.setId(4);

        chats.add(chat);
        chats.add(chat2);
        chats.add(chat3);
        chats.add(chat4);

        List<MessageEntity> messages = new ArrayList<>();

        List<UserEntity> users = new ArrayList<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        users.add(userEntity);

        MessageEntity message = new MessageEntity();
        message.setId(1);
        message.setChatId(4);
        message.setSenderId(1);
        message.setText("dfsdf");

        messages.add(message);

        db.beginTransaction();
        try {
            db.chatDao().insertAll(chats);
            db.userDao().insertAll(users);
            db.messageDao().insertAll(messages);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}
