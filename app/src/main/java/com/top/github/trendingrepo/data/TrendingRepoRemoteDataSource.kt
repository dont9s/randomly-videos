package com.top.github.trendingrepo.data

import com.top.github.api.BaseDataSource
import com.top.github.api.TopGithubService
import javax.inject.Inject

class TrendingRepoRemoteDataSource @Inject constructor(private val service: TopGithubService) : BaseDataSource() {

    public suspend fun fetchData() = getResult { service.getTrendingRepositories("java", "weekly") }
}