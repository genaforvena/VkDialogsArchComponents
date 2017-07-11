package org.imozerov.vkgroupdialogs.chat;

import android.arch.persistence.room.ColumnInfo;

import java.util.Date;

public class Message {
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "text")
    private String text;
    @ColumnInfo(name = "isMine")
    private boolean isMine;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "photoUrl")
    private String senderPhoto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }
}
