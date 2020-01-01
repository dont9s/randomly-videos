package com.randomly.videos.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.randomly.videos.trendingrepo.data.VideoPostRepository
import com.randomly.videos.trendingrepo.ui.VideoPostsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.hamcrest.core.IsNull.nullValue
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class VideoPostsViewModelTest {

    private val themeId = 567

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = mock(VideoPostRepository::class.java)
    private var viewModel = VideoPostsViewModel(repository)

    @Test
    fun testNull() {
        assertThat(viewModel.posts, nullValue())

    }


}