package com.instory.latest.trendingrepo.data

import com.instory.latest.data.resultLiveData
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingRepoRepository @Inject constructor(private val dao: TrendingRepoDao,
                                                 private val remoteSource: TrendingRepoRemoteDataSource) {
    val trendingRepos = resultLiveData(
            databaseQuery = { dao.getTrendingRepos() },
            networkCall = { remoteSource.fetchData() },
            saveCallResult = { dao.insertAll(it) })


}