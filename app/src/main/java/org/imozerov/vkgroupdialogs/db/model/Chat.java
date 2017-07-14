package org.imozerov.vkgroupdialogs.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import java.util.Date;

import javax.annotation.Nullable;

/**
 * Created by imozerov on 14/07/2017.
 */

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
    private Date lastMessageTime;
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

    public Date getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Date lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public Bitmap getPhotoFallback() {
        return photoFallback;
    }

    public void setPhotoFallback(Bitmap photoFallback) {
        this.photoFallback = photoFallback;
    }
}
