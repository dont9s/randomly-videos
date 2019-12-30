package com.randomly.videos.trendingrepo.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Repo(
        @field:SerializedName("name")
        val repoName: String? = null,
        @field:SerializedName("description")
        val description: String? = null,
        @field:SerializedName("url")
        val repoUrl: String? = null

) : Serializable{

    override fun toString(): String = repoName!!
}
