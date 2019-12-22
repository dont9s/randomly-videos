package com.top.github.trendingrepo.ui

import androidx.lifecycle.ViewModel
import com.top.github.trendingrepo.data.TrendingRepoRepository
import javax.inject.Inject


class TrendingRepoViewModel @Inject constructor(val repository: TrendingRepoRepository) : ViewModel() {

    val trendingRepos = repository.trendingRepos

}