package com.randomly.videos.trendingrepo.data

import com.randomly.videos.data.resultLiveData
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