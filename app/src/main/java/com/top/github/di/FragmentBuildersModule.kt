package com.top.github.di


import com.top.github.repodetail.ui.RepoDetailFragment
import com.top.github.trendingrepo.ui.TrendingRepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeTrendingRepoFragment(): TrendingRepoFragment

    @ContributesAndroidInjector
    abstract fun contributeRepoDetailFragment(): RepoDetailFragment

}
