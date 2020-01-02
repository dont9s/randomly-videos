package com.randomly.videos.trendingrepo.ui

import androidx.lifecycle.ViewModel
import com.randomly.videos.trendingrepo.data.VideoPostRepository
import javax.inject.Inject


class VideoPostsViewModel @Inject constructor(val repository: VideoPostRepository) : ViewModel() {

    val posts = repository.posts



}