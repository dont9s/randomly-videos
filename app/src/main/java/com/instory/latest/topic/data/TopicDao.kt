package com.instory.latest.topic.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TopicDao {

    @Query("SELECT * FROM topics ")
    fun getTopics(): LiveData<List<Topic>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(topics: List<Topic>)


    @Query("UPDATE topics SET isSelected = :isSelected WHERE topic = :topic")
    suspend fun updateTopicIsSelected(topic: String, isSelected: Boolean): Int
}