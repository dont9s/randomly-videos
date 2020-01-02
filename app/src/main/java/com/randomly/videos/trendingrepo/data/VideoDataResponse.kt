package com.randomly.videos.trendingrepo.data

import com.google.gson.annotations.SerializedName

data class VideoDataResponse(
        @field:SerializedName("posts") public val postList: List<Post>,
        @field:SerializedName("page")
        val page: Int? = null

){

}