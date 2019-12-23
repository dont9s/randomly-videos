package com.top.github.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.top.github.trendingrepo.data.TrendingRepoRepository
import com.top.github.trendingrepo.ui.TrendingRepoViewModel
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
class TrendingRepoViewModelTest {

    private val themeId = 567

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = mock(TrendingRepoRepository::class.java)
    private var viewModel = TrendingRepoViewModel(repository)

    @Test
    fun testNull() {
        assertThat(viewModel.trendingRepos, nullValue())

    }


}