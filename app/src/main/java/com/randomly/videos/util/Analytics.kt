package com.randomly.videos.util

import android.app.Application
import android.os.Bundle
import android.provider.Settings
import com.clevertap.android.sdk.CleverTapAPI
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.iid.FirebaseInstanceId
import com.randomly.videos.BuildConfig
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Analytics @Inject constructor(private var app: Application) {


    private var clevertapDefaultInstance: CleverTapAPI? = null
    private lateinit var firebaseAnalytics : FirebaseAnalytics

    init {
        try {
            clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(app)
            firebaseAnalytics = FirebaseAnalytics.getInstance(app)

        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
        }
    }


    @Synchronized
    fun sendEvent(event: String, hProperites: HashMap<String, Any?>?) {
        var hProperites = hProperites
        if (BuildConfig.DEBUG) return
        if (hProperites == null) hProperites = HashMap()
        val keySet: Set<String> = hProperites.keys
        for (rawKey in keySet) {

            if (hProperites[rawKey] == null || hProperites[rawKey].toString().isEmpty()) {
                hProperites[rawKey] = "NA"
            }
        }
        hProperites["callingSource"] = CALLING_APP
        clevertapDefaultInstance?.pushEvent(event, hProperites)
        val bParams = Bundle()
        for (o in hProperites.keys) {
            val key = o as String
            val `val` = hProperites[key]
            if (`val` is Int) {
                bParams.putInt(key, (`val` as Int?)!!)
            } else if (`val` is Boolean) {
                bParams.putBoolean(key, (`val` as Boolean?)!!)
            } else if (`val` is Double) {
                bParams.putDouble(key, (`val` as Double?)!!)
            } else if (`val` is Float) {
                bParams.putFloat(key, (`val` as Float?)!!)
            } else if (`val` is String) {
                bParams.putString(key, `val` as String?)
            }
        }
          if (bParams.keySet().size > 0) {
//              logger.logEvent(event, bParams)
              firebaseAnalytics.logEvent(formatEvent(event), bParams)
          } else {
//              logger.logEvent(event)
              firebaseAnalytics.logEvent(event, Bundle())
          }
    }

    @Synchronized
    private fun formatEvent(unformatted: String): String {
        var unformatted = unformatted
        unformatted = unformatted.trim { it <= ' ' }.toLowerCase()
        var formatted = ""
        for (index in 0 until unformatted.length) {
            var c = unformatted[index]
            if (!(c >= 'a' && c <= 'z' || c >= '0' && c <= '9')) {
                c = '_'
            }
            formatted += c
        }
        return formatted
    }


    @Synchronized
    fun updateUser() {
        val params = HashMap<String, Any?>()

        params[DEVICE_ID_KEY] = Settings.Secure.getString(app.getContentResolver(),
                Settings.Secure.ANDROID_ID)

        clevertapDefaultInstance?.pushProfile(params)
    }

    fun pushFCMToken() {
        val token = FirebaseInstanceId.getInstance().token
        clevertapDefaultInstance?.pushFcmRegistrationId(token, true)
    }

}