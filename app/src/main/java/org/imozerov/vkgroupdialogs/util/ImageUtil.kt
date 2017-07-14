package org.imozerov.vkgroupdialogs.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String?) {
    Glide.with(context)
            .load(url)
            // Please do not try use placeholder unless CircleImageView removed
//    https://github.com/bumptech/glide/issues/528
            .into(this)
}