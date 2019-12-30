package com.randomly.videos.binding

import android.graphics.BitmapFactory
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.randomly.videos.data.AppDatabase
import com.randomly.videos.trendingrepo.data.Image
import com.randomly.videos.util.Utils
import kotlinx.coroutines.*


@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("isGone")
fun bindIsGone(view: FloatingActionButton, isGone: Boolean?) {
    if (isGone == null || isGone) {
        view.hide()
    } else {
        view.show()
    }
}

@BindingAdapter("imageFromUrl")
fun ImageView.bindImageFromUrl(imageUrl: String) {
    /* val url = Utils.makeUrlHttps(imageUrl)

     val manager = ImageManager(context)


     manager.getBitmapFromDiskCache(url)?.also {
         this.setImageBitmap(it)
     }*/

    /*Glide.with(context)
            .load(imageUrl)
            .into(this)*/

    val database = AppDatabase.getInstance(context)


    runBlocking(Dispatchers.IO) {
        val res = async { database.imageDao().getImageByKey(Utils.md5(imageUrl)) }

        val image = res.await()


        if (image != null) {
            //   image exist
            val byteArray = image.array

            val bitmap = byteArray?.size?.let { BitmapFactory.decodeByteArray(byteArray, 0, it) }

            if (bitmap != null && bitmap.isRecycled)
                return@runBlocking
            this@bindImageFromUrl.setImageBitmap(bitmap)

        } else {
            //load image add to room and set to bitmap


            val res = async { Utils.httpGetBitmap(imageUrl) }


            val bitmap = res.await()

            val image = Image(Utils.md5(imageUrl), Utils.getByteArrayFromBitmap(bitmap))

            database.imageDao().insertImage(image)


            if (bitmap != null && bitmap.isRecycled)
                return@runBlocking
            this@bindImageFromUrl.setImageBitmap(bitmap)


        }

        /*val keyedBitmap = KeyedBitmap(bitmap, imageUrl)
        cache.add(keyedBitmap, object : BitmapDiskCache.EncodingCallback {
            override fun done(key: String?) {

            }

        })*/
    }


//    val cache = BitmapDiskCache(context)

    /* cache[imageUrl, object : BitmapDiskCache.DecodingCallback {
         override fun done(bitmap: Bitmap?) {
             if (bitmap != null)
                 this@bindImageFromUrl.setImageBitmap(bitmap)
             else {

             }
         }

     }]*/


/*

    manager.load(this.context,
            url,
            this)
*/

}

@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}


