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
        chats.add(chatEntity);

        db.beginTransaction();
        try {
            db.chatDao().insertAll(chats);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}
