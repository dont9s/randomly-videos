package com.randomly.videos.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class RepoApi

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScropeIO
