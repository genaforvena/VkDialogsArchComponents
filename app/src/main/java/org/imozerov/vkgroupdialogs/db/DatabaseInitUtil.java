package org.imozerov.vkgroupdialogs.db;

import org.imozerov.vkgroupdialogs.vo.Chat;
import org.imozerov.vkgroupdialogs.vo.Message;

import java.util.ArrayList;
import java.util.List;

class DatabaseInitUtil {
    static void initializeDb(AppDatabase db) {
        List<Chat> chats = new ArrayList<>();

        Chat chat = new Chat();
        chat.setId(1);

        Chat chat2 = new Chat();
        chat2.setId(2);

        Chat chat3 = new Chat();
        chat3.setId(3);

        Chat chat4 = new Chat();
        chat4.setId(4);

        chats.add(chat);
        chats.add(chat2);
        chats.add(chat3);
        chats.add(chat4);

        List<Message> messages = new ArrayList<>();

        Message message = new Message();
        message.setId(1);
        message.setChatId(4);
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
