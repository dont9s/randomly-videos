package com.top.github.util

class Utils {

    companion object {
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
    }
}