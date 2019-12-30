package com.randomly.videos.trendingrepo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: Image)

    @Query("SELECT * FROM images where imgKey =:key")
    suspend fun getImageByKey(key: String): Image


}