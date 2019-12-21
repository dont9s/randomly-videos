package com.instory.latest.topic.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "topics")
data class Topic(
        @field:SerializedName("name")
        val name: String? = null,
        @PrimaryKey
        @field:SerializedName("topic")
        val topic: String,
        @field:SerializedName("imgUrl")
        val imgUrl: String? = null,
        @field:SerializedName("isSelected")
        val isSelected: Boolean = false
) {
    override fun toString() = topic


}