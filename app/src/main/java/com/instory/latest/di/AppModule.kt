package com.instory.latest.di

import android.app.Application
import android.content.Context
import com.clevertap.android.sdk.CleverTapAPI
import com.instory.latest.api.AuthInterceptor
import com.instory.latest.api.InstoryService
import com.instory.latest.data.AppDatabase
import com.instory.latest.trending.viral.news.BuildConfig
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.annotation.Nullable
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideInstoryService(@LegoAPI okhttpClient: OkHttpClient,
                              converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, InstoryService::class.java)


    @LegoAPI
    @Provides
    fun providePrivateOkHttpClient(
            upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
                .addInterceptor(AuthInterceptor(BuildConfig.API_DEVELOPER_TOKEN)).build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)


    @Singleton
    @Provides
    fun provideTopicDao(db: AppDatabase) = db.topicDao()


    @CoroutineScropeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)


    private fun createRetrofit(
            okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(InstoryService.ENDPOINT)
                .client(okhttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }

    private fun <T> provideService(okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }


}
