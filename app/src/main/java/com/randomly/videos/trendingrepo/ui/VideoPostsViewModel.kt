package com.randomly.videos.trendingrepo.ui

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.randomly.videos.App
import com.randomly.videos.api.RandmolyApiSerivice
import com.randomly.videos.data.AppDatabase
import com.randomly.videos.trendingrepo.data.Post
import com.randomly.videos.trendingrepo.data.VideoPostRepository
import com.randomly.videos.util.pageMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class VideoPostsViewModel @Inject constructor(val repository: VideoPostRepository) : AndroidViewModel(App.instance) {

    @Inject
    lateinit var service: RandmolyApiSerivice

    var currentPage = 0

    var loading: Boolean = true

    val posts = repository.posts

    fun loadMore() {


        currentPage++

        if (currentPage >= pageMap.size)
            return

        runBlocking(Dispatchers.IO) {
            val response = async { pageMap[currentPage]?.let { service.getVideoPosts(it) } }

            val result = response.await()

            if (result?.isSuccessful!!) {
                val posts = result.body()?.postList

                val database = AppDatabase.getInstance(getApplication())

                posts?.let { database.trendingRepoDao().insertAll(it) }

                loading = true

            } else {

            }

        }

    }

    fun sort(sortBy: Int) {

        when (sortBy) {
            VideoPostsFragment.BY_DATE -> {

                val list = mutableListOf<Post>()
                posts.value?.data?.let { list.addAll(it) }
                Log.e("sortByDate", "listSize : " + list.size)


                list.sortWith(Comparator { t, t2 ->
                    return@Comparator (t.eventDate?.minus(t2.eventDate!!))?.toInt()!!
                })

                val database = AppDatabase.getInstance(getApplication())

                runBlocking(Dispatchers.IO) {

                    database.trendingRepoDao().nukeTable()

                    database.trendingRepoDao().insertAll(list)
                }
            }
            VideoPostsFragment.BY_LIKES -> {

                val list = mutableListOf<Post>()
                posts.value?.data?.let { list.addAll(it) }

                list.sortWith(Comparator { t, t2 ->
                    return@Comparator (t.likes?.minus(t2.likes!!))?.toInt()!!
                })

                val database = AppDatabase.getInstance(getApplication())

                runBlocking(Dispatchers.IO) {

                    database.trendingRepoDao().nukeTable()

                    database.trendingRepoDao().insertAll(list)
                }

            }
            VideoPostsFragment.BY_SHARES -> {

                val list = mutableListOf<Post>()
                posts.value?.data?.let { list.addAll(it) }

                list.sortWith(Comparator { t, t2 ->
                    return@Comparator (t.shares?.minus(t2.shares!!))?.toInt()!!
                })

                val database = AppDatabase.getInstance(getApplication())

                runBlocking(Dispatchers.IO) {

                    database.trendingRepoDao().nukeTable()

                    database.trendingRepoDao().insertAll(list)
                }

            }
            VideoPostsFragment.BY_VIEWS -> {
                val list = mutableListOf<Post>()
                posts.value?.data?.let { list.addAll(it) }

                list.sortWith(Comparator { t, t2 ->
                    return@Comparator (t.views?.minus(t2.views!!))?.toInt()!!
                })

                val database = AppDatabase.getInstance(getApplication())

                runBlocking(Dispatchers.IO) {

                    database.trendingRepoDao().nukeTable()

                    database.trendingRepoDao().insertAll(list)
                }

            }
        }

    }


}