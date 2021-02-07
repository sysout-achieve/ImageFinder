package com.gunt.imagefinder.util.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.gunt.imagefinder.R

object ImageViewExt {
    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(thumbnailImgView: ImageView, imgUrl: String?) {
        Glide.with(thumbnailImgView)
            .load(imgUrl)
            .thumbnail(0.2f)
            .error(R.drawable.ic_launcher_foreground) // Error Image 있을 경우 변경
            .into(thumbnailImgView)
    }
}
