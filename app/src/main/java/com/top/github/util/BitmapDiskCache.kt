package com.top.github.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Environment
import android.os.Environment.isExternalStorageRemovable
import com.jakewharton.disklrucache.DiskLruCache
import com.top.github.data.KeyedBitmap
import java.io.File
import java.io.IOException

class BitmapDiskCache(context: Context) {
    private var mDiskLruCache: DiskLruCache? = null
    private val mDiskCacheLock = Object()
    private var mDiskCacheStarting = true

    private inner class InitDiskCacheTask : AsyncTask<File?, Void?, Void?>() {
        override fun doInBackground(vararg params: File?): Void? {
            synchronized(mDiskCacheLock) {
                val cacheDir: File? = params[0]
                try {
                    mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1, DISK_CACHE_SIZE)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                mDiskCacheLock.notifyAll() // Wake any waiting threads
            }
            return null
        }

        override fun onPostExecute(aVoid: Void?) {
            mDiskCacheStarting = false // Finished initialization
        }
    }

    interface DecodingCallback {
        fun done(bitmap: Bitmap?)
    }

    operator fun get(key: String?, callback: DecodingCallback) {
        DecodingTask(callback).execute(key)
    }

    private inner class DecodingTask(private val callback: DecodingCallback) : AsyncTask<String?, Void?, Bitmap?>() {
        // Decode image in background.


        override fun onPostExecute(bitmap: Bitmap?) {
            callback.done(bitmap)
        }

        override fun doInBackground(vararg p0: String?): Bitmap? {
            val imageKey = p0[0]
            var foundSnapshot: DiskLruCache.Snapshot? = null
            synchronized(mDiskCacheLock) {
                // Wait while disk cache is started from background thread
                while (mDiskCacheStarting) {
                    try {
                        mDiskCacheLock.wait()
                    } catch (ignored: InterruptedException) {
                    }
                }
                if (mDiskLruCache != null) {
                    try {
                        foundSnapshot = mDiskLruCache!!.get(imageKey)
                    } catch (e: Exception) {
                    }
                }
            }
            if (foundSnapshot != null) try {
                foundSnapshot.use { snapshot -> return BitmapFactory.decodeStream(snapshot?.getInputStream(0)) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

    }

    interface EncodingCallback {
        fun done(key: String?)
    }

    fun add(keyedBitmap: KeyedBitmap?, callback: EncodingCallback) {
        EncodingTask(callback).execute(keyedBitmap)
    }

    private inner class EncodingTask(private val callback: EncodingCallback) : AsyncTask<KeyedBitmap?, Void?, String?>() {


        override fun onPostExecute(key: String?) {
            callback.done(key)
        }

        override fun doInBackground(vararg p0: KeyedBitmap?): String? {
            synchronized(mDiskCacheLock) {
                var editor: DiskLruCache.Editor? = null
                try {
                    val key: String? = p0[0]?.key
                    if (mDiskLruCache != null && mDiskLruCache!!.get(key) == null) {
                        editor = mDiskLruCache!!.edit(key)
                        p0[0]?.bitmap?.compress(Bitmap.CompressFormat.PNG, 95, editor.newOutputStream(0))
                        editor.commit()
                        return key
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    if (editor != null) {
                        try {
                            editor.abort()
                        } catch (ignored: IOException) {
                        }
                    }
                }
                return null
            }
        }

    }

    fun close() {
        synchronized(mDiskCacheLock) {
            try {
                if (!(mDiskLruCache?.isClosed)!!) {
                    mDiskLruCache?.close()
                }
                mDiskLruCache?.delete()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val DISK_CACHE_SIZE = 1024 * 1024 * 20 // 20MB
                .toLong()
        private const val DISK_CACHE_SUBDIR = "BITMAP_CACHE"
        // Creates a unique subdirectory of the designated app cache directory. Tries to use external
// but if not mounted, falls back on internal storage.
        private fun getDiskCacheDir(context: Context): File { // Check if media is mounted or storage is built-in, if so, try and use external cache dir
// otherwise use internal cache dir
            val cachePath: String? = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() ||
                    !isExternalStorageRemovable()) context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path else context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path
            return File(cachePath + File.separator.toString() + DISK_CACHE_SUBDIR)
        }
    }

    init {
        val cacheDir: File = getDiskCacheDir(context)
        InitDiskCacheTask().execute(cacheDir)
    }
}