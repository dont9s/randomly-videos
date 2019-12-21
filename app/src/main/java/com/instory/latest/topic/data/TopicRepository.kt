package com.instory.latest.topic.data

import com.instory.latest.data.resultLiveData
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicRepository @Inject constructor(private val dao: TopicDao,
                                          private val remoteSource: TopicRemoteDataSource) {
    val topics = resultLiveData(
            databaseQuery = { dao.getTopics() },
            networkCall = { remoteSource.fetchData() },
            saveCallResult = { dao.insertAll(it) })

    fun updateTopicIsSeleceted(topic: String, isSelected: Boolean) {
        runBlocking {
            dao.updateTopicIsSelected(topic, isSelected)
        }

    }

}