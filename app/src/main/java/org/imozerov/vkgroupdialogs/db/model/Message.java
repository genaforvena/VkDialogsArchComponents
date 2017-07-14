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
    @ColumnInfo(name = "senderId")
    private long senderId;

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

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (isMine != message.isMine) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        if (date != null ? !date.equals(message.date) : message.date != null) return false;
        if (photo != null ? !photo.equals(message.photo) : message.photo != null) return false;
        return senderPhoto != null ? senderPhoto.equals(message.senderPhoto) : message.senderPhoto == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (isMine ? 1 : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (senderPhoto != null ? senderPhoto.hashCode() : 0);
        return result;
    }
}
