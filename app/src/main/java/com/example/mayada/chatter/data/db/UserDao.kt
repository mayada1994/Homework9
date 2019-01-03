package com.example.mayada.chatter.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao{

    @Insert
    fun insert(user: User)

    @Query("SELECT * from users ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<User>>
}