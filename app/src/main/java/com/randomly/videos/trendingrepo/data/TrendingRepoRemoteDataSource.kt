package com.randomly.videos.trendingrepo.data

import com.randomly.videos.api.BaseDataSource
import com.randomly.videos.api.TopGithubService
import javax.inject.Inject

class TrendingRepoRemoteDataSource @Inject constructor(private val service: TopGithubService) : BaseDataSource() {

    public suspend fun fetchData() = getResult { service.getTrendingRepositories("java", "weekly") }
}