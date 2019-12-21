package com.instory.latest.api

import com.instory.latest.topic.data.Topic
import retrofit2.Response
import retrofit2.http.GET

/**
 * Lego REST API access points
 */
interface InstoryService {

    companion object {
        const val ENDPOINT = "https://www.foodbox.io/"
    }


    @GET("stories/getTopicsInstory.php")
    suspend fun getTopics(): Response<List<Topic>>

}
