package com.instory.latest.trendingrepo.data

import com.instory.latest.api.BaseDataSource
import com.instory.latest.api.TopGithubService
import javax.inject.Inject

class TrendingRepoRemoteDataSource @Inject constructor(private val service: TopGithubService) : BaseDataSource() {

    public suspend fun fetchData() = getResult { service.getTrendingRepositories("java", "weekly") }
}