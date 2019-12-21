package com.instory.latest.topic.ui

import androidx.lifecycle.ViewModel
import com.instory.latest.topic.data.TopicRepository
import javax.inject.Inject


class TopicViewModel @Inject constructor(val  repository: TopicRepository) : ViewModel() {

    val topics = repository.topics

}