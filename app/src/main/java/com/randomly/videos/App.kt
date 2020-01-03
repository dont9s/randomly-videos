package com.randomly.videos

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.randomly.videos.di.AppInjector
import com.randomly.videos.util.CrashReportingTree
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject


class App : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


   companion object{
       lateinit var instance : App
   }



    override fun onCreate() {
        super.onCreate()

        instance = this

        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        else Timber.plant(CrashReportingTree())



        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}