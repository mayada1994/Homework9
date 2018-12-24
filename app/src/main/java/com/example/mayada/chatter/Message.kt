package com.example.mayada.chatter

class Message(private val user: Int, private var text: String){
    var messageText: String
        get() = this.text
        set(value: String){this.text=value}

    val messageUser: Int
        get() = this.user
}