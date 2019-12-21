package com.instory.latest.util

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.Target
import timber.log.Timber

class ImageManager {


    fun load(context: Context, url: String?, bitmapImageViewTarget: BitmapImageViewTarget) {
        if (null == url || context == null) return
        if (context is Activity && context.isDestroyed) return
        if (url.length < 4) {
            return
        } else {
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.IMMEDIATE)
                    .dontAnimate()
                    .into(bitmapImageViewTarget)
        }
    }

    fun load(context: Context, placeHolder: String?, url: String?, imageView: ImageView, listener: RequestListener<Drawable>?) {
        if (null == url || context == null) return
        if (context is Activity && context.isDestroyed) return
        if (url.length < 4) {
            return
        } else {
            var loader: RequestBuilder<Drawable> = Glide.with(context)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .listener(listener)
            if (placeHolder != null) loader = loader.thumbnail(Glide.with(context).load(placeHolder))
            loader = if (url.endsWith(".gif")) {
                loader.diskCacheStrategy(DiskCacheStrategy.DATA)
            } else {
                loader.diskCacheStrategy(DiskCacheStrategy.ALL)
            }
            loader.into(imageView)
        }
    }

    fun load(context: Context, url: String?, imageView: ImageView) {
        load(context, null, url, imageView, null)
    }

    fun load(context: Context, url: String?, imageView: ImageView, listener: RequestListener<Drawable>?) {
        load(context, null, url, imageView, listener)
    }


    companion object {
        private val TAG = ImageManager::class.java.simpleName
    }
}