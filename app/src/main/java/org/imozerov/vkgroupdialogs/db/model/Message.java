package org.imozerov.vkgroupdialogs.db.model;

import android.arch.persistence.room.ColumnInfo;

import java.util.Date;

import javax.annotation.Nullable;

public class Message {
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "text")
    private String text;
    @ColumnInfo(name = "isMine")
    private boolean isMine;
    @ColumnInfo(name = "date")
    private Date date;
    @Nullable
    @ColumnInfo(name = "photo")
    private String photo;
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

    @Nullable
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(@Nullable String photo) {
        this.photo = photo;
    }
}
