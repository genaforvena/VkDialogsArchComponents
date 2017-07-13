package org.imozerov.vkgroupdialogs.util

import android.graphics.Bitmap
import android.graphics.Canvas

class CollageCreator {
    fun createCollage(bitmaps: List<Bitmap>) : Bitmap? {
        var result: Bitmap? = null

        when (bitmaps.size) {
            0 -> { }
            1 -> result = bitmaps[0]
            2 -> result = BitmapUtils.joinBitmapsHorizontally(bitmaps[0], bitmaps[1])
            3 -> {
                val horizontally = BitmapUtils.joinBitmapsVertically(bitmaps[0], bitmaps[1])

                var resizedBitmap = Bitmap.createScaledBitmap(bitmaps[2],
                        horizontally.height, horizontally.height, false)
                resizedBitmap = BitmapUtils.getVkAvatarBitmap(resizedBitmap)

                result = BitmapUtils.joinBitmapsHorizontally(resizedBitmap, horizontally)
            }
            4 -> {
                val horizontallyFirst = BitmapUtils.joinBitmapsHorizontally(bitmaps[0], bitmaps[1])
                val horizontallySecond = BitmapUtils.joinBitmapsHorizontally(bitmaps[2], bitmaps[3])
                result = BitmapUtils.joinBitmapsVertically(horizontallyFirst, horizontallySecond)
            }
        }

        return result
    }
}

private object BitmapUtils {
    val LINE = 5

    fun joinBitmapsHorizontally(c: Bitmap, s: Bitmap): Bitmap {
        val cs: Bitmap
        val width: Int
        val height: Int

        if (c.width > s.width) {
            width = c.width + s.width
            height = c.height
        } else {
            width = s.width + s.width
            height = c.height
        }

        cs = Bitmap.createBitmap(width + LINE, height, Bitmap.Config.ARGB_8888)
        val comboImage = Canvas(cs)

        comboImage.drawBitmap(c, 0f, 0f, null)
        comboImage.drawBitmap(s, (c.width + LINE).toFloat(), 0f, null)
        return cs
    }


    fun joinBitmapsVertically(c: Bitmap, s: Bitmap): Bitmap {
        val cs: Bitmap
        val width: Int
        val height: Int

        if (c.height > s.height) {
            height = c.height + s.height
            width = c.width
        } else {
            height = s.height + s.height
            width = c.width
        }

        cs = Bitmap.createBitmap(width, height + LINE, Bitmap.Config.ARGB_8888)
        val comboImage = Canvas(cs)

        comboImage.drawBitmap(c, 0f, 0f, null)
        comboImage.drawBitmap(s, 0f, (c.height + LINE).toFloat(), null)
        return cs
    }

    fun getVkAvatarBitmap(source: Bitmap): Bitmap {
        val result = Bitmap.createBitmap(source, 0, 0, source.width / 2, source.height)
        return result
    }
}
