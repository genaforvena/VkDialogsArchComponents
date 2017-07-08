package org.imozerov.vkgroupdialogs.persistance.db;

import org.imozerov.vkgroupdialogs.persistance.db.entities.ChatEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imozerov on 08/07/2017.
 */

class DatabaseInitUtil {
    static void initializeDb(AppDatabase db) {
        List<ChatEntity> chats = new ArrayList<>();

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(1);

        ChatEntity chatEntity2 = new ChatEntity();
        chatEntity2.setId(2);

        chats.add(chatEntity);
        chats.add(chatEntity2);

        db.beginTransaction();
        try {
            db.chatDao().insertAll(chats);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}
