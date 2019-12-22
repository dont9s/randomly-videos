package com.instory.latest.di


import com.instory.latest.trendingrepo.ui.TrendingRepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeTrendingRepoFragment(): TrendingRepoFragment

}
