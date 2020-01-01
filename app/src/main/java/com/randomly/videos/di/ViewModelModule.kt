package com.randomly.videos.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.randomly.videos.trendingrepo.ui.VideoPostsViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(VideoPostsViewModel::class)
    abstract fun bindTrendingRepoViewModel(viewModel: VideoPostsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
