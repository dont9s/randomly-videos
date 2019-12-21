package com.instory.latest.di


import com.instory.latest.topic.ui.TopicFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeTopicFragment(): TopicFragment

}
