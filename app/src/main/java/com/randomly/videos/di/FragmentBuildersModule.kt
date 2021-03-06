package com.randomly.videos.di


import com.randomly.videos.trendingrepo.ui.VideoPostsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeTrendingRepoFragment(): VideoPostsFragment


}
