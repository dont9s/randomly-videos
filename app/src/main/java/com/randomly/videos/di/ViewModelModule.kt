package com.randomly.videos.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.randomly.videos.trendingrepo.ui.TrendingRepoViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(TrendingRepoViewModel::class)
    abstract fun bindTrendingRepoViewModel(viewModel: TrendingRepoViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
