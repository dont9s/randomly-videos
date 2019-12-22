package com.instory.latest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.instory.latest.trendingrepo.data.TrendingRepoRepository
import com.instory.latest.trendingrepo.ui.TrendingRepoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class LegoSetViewModelTest {

    private val themeId = 567

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = mock(TrendingRepoRepository::class.java)
    private var viewModel = TrendingRepoViewModel(repository)

    @Test
    fun testNull() {
    /*    assertThat(viewModel.themeId, nullValue())
        assertThat(viewModel.connectivityAvailable, notNullValue())
        verify(repository, never()).observePagedSets(false, themeId, coroutineScope)
        verify(repository, never()).observePagedSets(true, themeId, coroutineScope)*/
    }

    @Test
    fun doNotFetchWithoutObservers() {
    /*    viewModel.themeId = themeId
        verify(repository, never()).observePagedSets(false, themeId, coroutineScope)*/
    }

    @Test
    fun doNotFetchWithoutObserverOnConnectionChange() {
        /*viewModel.themeId = themeId
        viewModel.connectivityAvailable = true

        verify(repository, never()).observePagedSets(true, themeId, coroutineScope)*/
    }

}