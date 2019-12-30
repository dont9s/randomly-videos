package com.randomly.videos.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Environment
import android.os.Environment.isExternalStorageRemovable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.BitmapImageViewTarget
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt


class ImageManager {

    companion object {
        private val TAG = ImageManager::class.java.simpleName

        private const val DISK_CACHE_SUBDIR = "thumbnails"
    }

    private var cacheDir: File


    private var context: Context

    constructor(context: Context) {
        this.context = context


        // Initialize disk cache on background thread
        cacheDir = getDiskCacheDir(context, DISK_CACHE_SUBDIR)


    }


    internal inner class BitmapWorkerTask : AsyncTask<String, Unit, Unit>() {

        override fun doInBackground(vararg p0: String) {

            return try {
                val url = URL(p0[0])

                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream


                val key: String = Utils.md5(p0[0])


                val file = File(cacheDir, key)
                file.createNewFile()

                val bos = ByteArrayOutputStream()

                BitmapFactory.decodeStream(input).compress(Bitmap.CompressFormat.PNG,
                        50 /*ignored for PNG*/,
                        bos)
                val bitmapdata = bos.toByteArray()

//write the bytes in file
                val fos = FileOutputStream(file)
                fos.write(bitmapdata)
                fos.flush()
                fos.close()
            } catch (e: IOException) { // Log exception

            }

        }
    }


    fun addBitmapToCache(url: String) {

        BitmapWorkerTask().execute(url)
    }

    fun getBitmapFromDiskCache(url: String): Bitmap? {

        val key = Utils.md5(url)
        val file = File(cacheDir.absolutePath + key)
        var bitmap: Bitmap? = null

        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(cacheDir.absolutePath + key)

        } else {
            addBitmapToCache(url)
        }
        return bitmap
    }

    // Creates a unique subdirectory of the designated app cache directory. Tries to use external
// but if not mounted, falls back on internal storage.
    fun getDiskCacheDir(context: Context, uniqueName: String): File {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        val cachePath =
                if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
                        || !isExternalStorageRemovable()) {
                    context.externalCacheDir?.path
                } else {
                    context.cacheDir.path
                }

        return File(cachePath + File.separator + uniqueName)
    }


    fun load(context: Context, url: String?, bitmapImageViewTarget: BitmapImageViewTarget) {
        if (null == url) return
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
        if (null == url) return
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


    fun decodeSampledBitmapFromResource(res: Resources?, resId: Int,
                                        reqWidth: Int, reqHeight: Int): Bitmap? { // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    fun calculateInSampleSize(
            options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int { // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            inSampleSize = if (width > height) {
                (height.toFloat() / reqHeight.toFloat()).roundToInt()
            } else {
                (width.toFloat() / reqWidth.toFloat()).roundToInt()
            }
        }
        return inSampleSize
    }


}