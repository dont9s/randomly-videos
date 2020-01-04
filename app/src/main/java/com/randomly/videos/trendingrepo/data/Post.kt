package com.randomly.videos.trendingrepo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.randomly.videos.util.Utils
import java.io.Serializable


@Entity(tableName = "posts")
data class Post(

        @field:SerializedName("id")
        val id: String,

        @field:SerializedName("thumbnail_image")
        val thumbnailImage: String? = null,
        @field:SerializedName("event_name")
        val eventName: String? = null,
        @PrimaryKey
        @field:SerializedName("event_date")
        val eventDate: Long? = null,
        @field:SerializedName("views")
        val views: Int? = null,

        @field:SerializedName("likes")
        val likes: Int? = null,
        @field:SerializedName("shares")

        val shares: Int? = null) : Serializable {


    override fun toString() = id + likes + eventDate

    fun getFormattedDate() = eventDate?.let { Utils.convertLongToTime(it) }

}