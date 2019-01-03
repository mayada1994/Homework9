package com.example.mayada.chatter.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mayada.chatter.data.MessageRepository

class MessageViewModelFactory(
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MessageViewModel::class.java) -> {
            MessageViewModel(application) as T
        }
        else -> throw IllegalArgumentException()
    }
}