package com.randomly.videos.trendingrepo.data

import com.randomly.videos.data.resultLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoPostRepository @Inject constructor(private val dao: PostDao,
                                              private val remoteSource: VideoPostRemoteDataSource) {
    val posts = resultLiveData(
            databaseQuery = { dao.getPosts() },
            networkCall = { remoteSource.fetchData() },
            saveCallResult = {

                dao.insertAll(it.postList)
            })


}