package com.top.github.trendingrepo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "images")
data class Image(
        @PrimaryKey
        @field:SerializedName("imgKey")
        val imgKey: String,

        @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "array")
        var array: ByteArray? = null) {


    override fun toString() = imgKey

}