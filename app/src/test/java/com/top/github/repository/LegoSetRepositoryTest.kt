package com.top.github.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.top.github.api.TopGithubService
import com.top.github.data.AppDatabase
import com.top.github.trendingrepo.data.TrendingRepoDao
import com.top.github.trendingrepo.data.TrendingRepoRemoteDataSource
import com.top.github.trendingrepo.data.TrendingRepoRepository
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
    private lateinit var repository: TrendingRepoRepository
    private val dao = mock(TrendingRepoDao::class.java)
    private val service = mock(TopGithubService::class.java)
    private val remoteDataSource = TrendingRepoRemoteDataSource(service)
    private val mockRemoteDataSource = spy(remoteDataSource)

    private val themeId = 456
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.trendingRepoDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = TrendingRepoRepository(dao, remoteDataSource)
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