package com.top.github

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.top.github.di.AppInjector
import com.top.github.BuildConfig
import com.top.github.util.CrashReportingTree
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject


class App : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        else Timber.plant(CrashReportingTree())



        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}