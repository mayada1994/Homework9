package com.example.mayada.chatter.data.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "messages")
data class Message (
    @PrimaryKey(autoGenerate = true)
    private val id: Int,
    val messageUser: Int,
    var messageText: String
)