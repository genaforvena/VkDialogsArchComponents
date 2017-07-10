package org.imozerov.vkgroupdialogs.db.converters;

import android.arch.persistence.room.TypeConverter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class BitmapConverter {
    @TypeConverter
    public static byte[] toByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return new byte[0];
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @TypeConverter
    public static Bitmap toBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
