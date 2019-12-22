package com.instory.latest.trendingrepo.data

import com.google.gson.annotations.SerializedName

data class Repo(
        @field:SerializedName("name")
        val repoName: String? = null,
        @field:SerializedName("description")
        val description: String? = null,
        @field:SerializedName("url")
        val repoUrl: String? = null

) {

    override fun toString(): String = repoName!!
}
