package com.randomly.videos.trendingrepo.ui

import androidx.lifecycle.ViewModel
import com.randomly.videos.trendingrepo.data.TrendingRepoRepository
import javax.inject.Inject


class TrendingRepoViewModel @Inject constructor(val repository: TrendingRepoRepository) : ViewModel() {

    val trendingRepos = repository.trendingRepos

}