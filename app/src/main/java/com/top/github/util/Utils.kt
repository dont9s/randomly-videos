package com.top.github.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class Utils {

    companion object {

        fun md5(s: String): String {
            try { // Create MD5 Hash
                val digest = MessageDigest.getInstance("MD5")
                digest.update(s.toByteArray())
                val messageDigest = digest.digest()
                // Create Hex String
                val hexString = StringBuffer()
                for (i in messageDigest.indices) hexString.append(Integer.toHexString(0xFF and messageDigest[i].toInt()))
                return hexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return ""
        }

        fun getByteArrayFromBitmap(bitmap: Bitmap?): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            bitmap?.recycle()

            return byteArray

        }


        fun makeUrlHttps(url: String): String {
            var newUrl = url

            if (url.indexOf("https://") == 0)
                return newUrl

            if (url.indexOf("http://") == 0) {
                // replace http with https
                newUrl = url.replaceFirst("http", "https")
            } else {
                newUrl = "https://$newUrl"
            }

            return newUrl
        }


        suspend fun httpGetBitmap(url: String?): Bitmap? {

            val inputStream: InputStream
            var result: Bitmap?

            // create URL
            val url = URL(url)

            // create HttpURLConnection
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

            // make GET request to the given URL
            conn.connect()

            // receive response as inputStream
            inputStream = conn.inputStream

            // convert inputstream to string
            if (inputStream != null) {

                val bufferedInputStream = BufferedInputStream(inputStream)

                result = BitmapFactory.decodeStream(bufferedInputStream);

            } else
                result = null

            return result
        }

    }


}