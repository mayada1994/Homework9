package com.example.mayada.chatter.data.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface UserDao{
    @Query("SELECT * from users")
    fun getAllUsers(): LiveData<ArrayList<User>>

    @Insert
    fun insert(word: User)

    @Query("DELETE FROM users")
    fun deleteAll()
}