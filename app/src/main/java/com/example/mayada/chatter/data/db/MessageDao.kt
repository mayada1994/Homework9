package com.example.mayada.chatter.data.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MessageDao{
    @Insert
    fun insert(message: Message)

    @Update
    fun update(message: Message)

    @Delete
    fun delete(message: Message)

    @Query("SELECT * from messages ORDER BY id ASC")
    fun getAllMessages(): LiveData<List<Message>>

}