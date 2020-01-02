package com.randomly.videos.trendingrepo.data

import com.randomly.videos.api.BaseDataSource
import com.randomly.videos.api.RandmolyApiSerivice
import com.randomly.videos.util.pageMap
import javax.inject.Inject

class VideoPostRemoteDataSource @Inject constructor(private val service: RandmolyApiSerivice) : BaseDataSource() {

    public suspend fun fetchData() = getResult { service.getVideoPosts(pageMap[0]!!) }
}