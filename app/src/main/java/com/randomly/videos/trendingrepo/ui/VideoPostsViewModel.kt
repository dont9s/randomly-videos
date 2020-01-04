package com.randomly.videos.trendingrepo.ui

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.randomly.videos.App
import com.randomly.videos.api.RandmolyApiSerivice
import com.randomly.videos.data.AppDatabase
import com.randomly.videos.trendingrepo.data.VideoPostRepository
import com.randomly.videos.util.pageMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject


class VideoPostsViewModel @Inject constructor(val repository: VideoPostRepository) : AndroidViewModel(App.instance) {

    @Inject
    lateinit var service: RandmolyApiSerivice

    var currentPage = 0

    var loading: Boolean = true

    val posts = repository.posts

    var currentOrder: Int = VideoPostsFragment.BY_NONE

    fun loadMore() {


        currentPage++

        if (currentPage >= pageMap.size)
            return


        try {
            runBlocking(Dispatchers.IO) {
                val response = async { pageMap[currentPage]?.let { service.getVideoPosts(it) } }


                val result = response.await()

                if (result?.isSuccessful!!) {
                    val posts = result.body()?.postList

                    val database = AppDatabase.getInstance(getApplication())

                    posts?.let { database.postDao().insertAll(it) }

                    loading = true

                } else {


                }


            }
        } catch (e: Exception) {

        }

    }


}