package com.randomly.videos.di


import com.randomly.videos.repodetail.ui.RepoDetailFragment
import com.randomly.videos.trendingrepo.ui.TrendingRepoFragment
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
