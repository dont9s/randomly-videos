package com.instory.latest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.instory.latest.topic.data.TopicDao
import com.instory.latest.util.getValue
import com.instory.latest.util.legoThemeId
import com.instory.latest.util.testTopicA
import com.instory.latest.util.testTopicB
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LegoSetDaoTest : DbTest() {
    private lateinit var topicDao: TopicDao
    private val setA = testTopicA.copy()
    private val setB = testTopicB.copy()
    private val themeId = legoThemeId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        topicDao = db.topicDao()

        // Insert legoSets in non-alphabetical order to test that results are sorted by name
        runBlocking {
            topicDao.insertAll(listOf(setA, setB))
        }
    }

    @Test
    fun testGetSets() {
        val list = getValue(topicDao.getTopics())
        assertThat(list.size, equalTo(2))

        // Ensure legoSet list is sorted by name
        assertThat(list[0], equalTo(setA))
        assertThat(list[1], equalTo(setB))
    }

    @Test
    fun testGetLegoSet() {
        assertThat(getValue(topicDao.getTopics()), equalTo(setA))
    }
}