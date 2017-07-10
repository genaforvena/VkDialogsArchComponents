package org.imozerov.vkgroupdialogs.chat;

import android.arch.persistence.room.Embedded;

import org.imozerov.vkgroupdialogs.db.entities.MessageEntity;

/**
 * Created by imozerov on 10/07/2017.
 */

public class Message {
    @Embedded
    private MessageEntity self;
    private String senderPhoto;

    public MessageEntity getSelf() {
        return self;
    }

    public void setSelf(MessageEntity self) {
        this.self = self;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }
}
