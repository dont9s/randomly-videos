package com.randomly.videos.trendingrepo.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "users")
data class User(
        @PrimaryKey
        @field:SerializedName("username")
        val username: String,

        @field:SerializedName("name")
        val name: String? = null,
        @field:SerializedName("type")
        val type: String? = null,
        @field:SerializedName("url")
        val url: String? = null,
        @field:SerializedName("avatar")
        val avatar: String? = null,
        @Embedded
        @field:SerializedName("repo")
        val repo: Repo? = null) : Serializable {


    override fun toString() = username

}