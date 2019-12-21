package com.instory.latest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.instory.latest.topic.ui.TopicViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(TopicViewModel::class)
    abstract fun bindTopicViewModel(viewModel: TopicViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
