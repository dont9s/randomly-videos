package com.instory.latest.trendingrepo.ui

import androidx.lifecycle.ViewModel
import com.instory.latest.trendingrepo.data.TrendingRepoRepository
import javax.inject.Inject


class TrendingRepoViewModel @Inject constructor(val repository: TrendingRepoRepository) : ViewModel() {

    val trendingRepos = repository.trendingRepos

}