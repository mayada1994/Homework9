package com.example.mayada.chatter.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @ColumnInfo(name = "message_user")
    val messageUser: Int,
    @ColumnInfo(name = "message_text")
    var messageText: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}