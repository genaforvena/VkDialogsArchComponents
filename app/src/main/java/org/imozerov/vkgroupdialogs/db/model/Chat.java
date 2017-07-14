package org.imozerov.vkgroupdialogs.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.graphics.Bitmap;

import javax.annotation.Nullable;

public class Chat {
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @Nullable
    @ColumnInfo(name = "photo")
    private String photo;
    @ColumnInfo(name = "lastMessageText")
    private String lastMessageText;
    @ColumnInfo(name = "lastMessageTime")
    private long lastMessageTime;
    @ColumnInfo(name = "collage")
    private Bitmap photoFallback;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(@Nullable String photo) {
        this.photo = photo;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
    }

    public long getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public Bitmap getPhotoFallback() {
        return photoFallback;
    }

    public void setPhotoFallback(Bitmap photoFallback) {
        this.photoFallback = photoFallback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        if (id != chat.id) return false;
        if (lastMessageTime != chat.lastMessageTime) return false;
        if (name != null ? !name.equals(chat.name) : chat.name != null) return false;
        if (photo != null ? !photo.equals(chat.photo) : chat.photo != null) return false;
        return lastMessageText != null ? lastMessageText.equals(chat.lastMessageText) : chat.lastMessageText == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (lastMessageText != null ? lastMessageText.hashCode() : 0);
        result = 31 * result + (int) (lastMessageTime ^ (lastMessageTime >>> 32));
        return result;
    }
}
