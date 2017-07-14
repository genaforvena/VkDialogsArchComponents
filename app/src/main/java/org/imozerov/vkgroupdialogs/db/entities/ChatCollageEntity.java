package org.imozerov.vkgroupdialogs.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

@Entity(tableName = "collages", indices = { @Index("id")})
public class ChatCollageEntity {
    @PrimaryKey
    private long id;
    private Bitmap collage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bitmap getCollage() {
        return collage;
    }

    public void setCollage(Bitmap collage) {
        this.collage = collage;
    }
}
