package com.randomly.videos.api

import com.randomly.videos.trendingrepo.data.Post
import com.randomly.videos.trendingrepo.data.User
import com.randomly.videos.trendingrepo.data.VideoDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Repo REST API access points
 */
interface RandmolyApiSerivice {

    companion object {
        const val ENDPOINT = "http://www.mocky.io/"
    }


    @GET("v2/{page}")
    suspend fun getVideoPosts(@Path("page") page: String): Response<VideoDataResponse>

}
