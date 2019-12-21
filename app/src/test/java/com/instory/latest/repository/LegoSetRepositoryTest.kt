package com.instory.latest.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.instory.latest.api.InstoryService
import com.instory.latest.data.AppDatabase
import com.instory.latest.topic.data.TopicDao
import com.instory.latest.topic.data.TopicRemoteDataSource
import com.instory.latest.topic.data.TopicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class LegoSetRepositoryTest {
    private lateinit var repository: TopicRepository
    private val dao = mock(TopicDao::class.java)
    private val service = mock(InstoryService::class.java)
    private val remoteDataSource = TopicRemoteDataSource(service)
    private val mockRemoteDataSource = spy(remoteDataSource)

    private val themeId = 456
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.topicDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = TopicRepository(dao, remoteDataSource)
    }

    @Test
    fun loadLegoSetsFromNetwork() {
        runBlocking {
          /*  repository.topics(connectivityAvailable = true,
                    themeId = themeId, coroutineScope = coroutineScope)

            verify(dao, never()).getLegoSets(themeId)
            verifyZeroInteractions(dao)*/
        }
    }

}