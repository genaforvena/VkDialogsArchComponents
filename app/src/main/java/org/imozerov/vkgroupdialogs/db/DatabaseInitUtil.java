package org.imozerov.vkgroupdialogs.db;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.Glide;

import org.imozerov.vkgroupdialogs.R;
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity;
import org.imozerov.vkgroupdialogs.db.entities.ChatUserRelationEntity;
import org.imozerov.vkgroupdialogs.db.entities.MessageEntity;
import org.imozerov.vkgroupdialogs.db.entities.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO Remove me as soon as true data will be available
public class DatabaseInitUtil {
    public static void initializeDb(Context context, AppDatabase db) {
        List<ChatEntity> chats = new ArrayList<>();

        ChatEntity chat = new ChatEntity();
        chat.setId(1);

        ChatEntity chat2 = new ChatEntity();
        chat2.setId(2);
        chat2.setAvatar(BitmapFactory.decodeResource(context.getResources(),
                com.facebook.stetho.R.drawable.abc_ab_share_pack_mtrl_alpha));

        ChatEntity chat3 = new ChatEntity();
        chat3.setId(3);

        ChatEntity chat4 = new ChatEntity();
        chat4.setId(4);
        chat4.setAvatar(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_launcher_background));
        chat4.setLastMessageText("Hello");
        chat4.setName("Test");
        chat4.setLastMessageTime(new Date(23242342l));

        chats.add(chat);
        chats.add(chat2);
        chats.add(chat3);
        chats.add(chat4);

        List<MessageEntity> messages = new ArrayList<>();

        List<UserEntity> users = new ArrayList<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setPhotoUrl("https://camo.mybb.com/e01de90be6012adc1b1701dba899491a9348ae79/687474703a2f2f7777772e6a71756572797363726970742e6e65742f696d616765732f53696d706c6573742d526573706f6e736976652d6a51756572792d496d6167652d4c69676874626f782d506c7567696e2d73696d706c652d6c69676874626f782e6a7067");

        users.add(userEntity);

        MessageEntity message = new MessageEntity();
        message.setId(1);
        message.setChatId(4);
        message.setSenderId(1);
        message.setDate(new Date(1121321l));
        message.setText("dfsdf");

        MessageEntity message2 = new MessageEntity();
        message2.setId(2);
        message2.setChatId(4);
        message2.setSenderId(1);
        message2.setDate(new Date(342342342l));
        message2.setText("Hello");
        message2.setMine(true);

        messages.add(message);
        messages.add(message2);

        List<ChatUserRelationEntity> chatUserRelationEntities = new ArrayList<>();
        ChatUserRelationEntity chatUserRelationEntity = new ChatUserRelationEntity();
        chatUserRelationEntity.setChatId(4);
        chatUserRelationEntity.setUserId(1);

        chatUserRelationEntities.add(chatUserRelationEntity);

        db.beginTransaction();
        try {
            db.chatDao().insertAll(chats);
            db.userDao().insertAll(users);
            db.messageDao().insertAll(messages);
            db.chatUserRelationDao().insertAll(chatUserRelationEntities);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}
