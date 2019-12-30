package com.randomly.videos.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.randomly.videos.trendingrepo.data.TrendingRepoDao
import com.randomly.videos.util.getValue
import com.randomly.videos.util.testUserA
import com.randomly.videos.util.testUserB
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrendingRepoDaoTest : DbTest() {
    private lateinit var trendingRepoDao: TrendingRepoDao
    private val setA = testUserA.copy()
    private val setB = testUserB.copy()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        trendingRepoDao = db.trendingRepoDao()

        runBlocking {
            trendingRepoDao.insertAll(listOf(setA, setB))
        }
    }

    @Test
    fun testGetSets() {
        val list = getValue(trendingRepoDao.getTrendingRepos())
        assertThat(list.size, equalTo(2))

        assertThat(list[0], equalTo(setA))
        assertThat(list[1], equalTo(setB))
    }

}