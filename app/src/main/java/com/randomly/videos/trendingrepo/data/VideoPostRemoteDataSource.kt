package com.randomly.videos.trendingrepo.data

import com.randomly.videos.api.BaseDataSource
import com.randomly.videos.api.RandmolyApiSerivice
import javax.inject.Inject

class VideoPostRemoteDataSource @Inject constructor(private val service: RandmolyApiSerivice) : BaseDataSource() {

    public suspend fun fetchData(page: String) = getResult { service.getVideoPosts(page) }
}