package com.instory.latest.topic.data

import com.instory.latest.api.BaseDataSource
import com.instory.latest.api.InstoryService
import javax.inject.Inject

class TopicRemoteDataSource @Inject constructor(private val service: InstoryService) : BaseDataSource() {

    public suspend fun fetchData() = getResult { service.getTopics() }
}