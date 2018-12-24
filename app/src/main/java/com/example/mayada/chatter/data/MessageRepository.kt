package com.example.mayada.chatter.data

import android.os.AsyncTask
import android.arch.lifecycle.LiveData
import android.app.Application

import com.example.mayada.chatter.data.db.ChatterDatabase
import com.example.mayada.chatter.data.db.Message
import com.example.mayada.chatter.data.db.MessageDao


class MessageRepository internal constructor(application: Application) {

    private val mMessageDao: MessageDao
    internal val allMessages: LiveData<ArrayList<Message>>

    init {
        val db = ChatterDatabase.invoke(application)
        mMessageDao = db.messageDao()
        allMessages = mMessageDao.getAllMessages()
    }


    fun insert(message: Message) {
        insertAsyncTask(mMessageDao).execute(message)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: MessageDao) :
        AsyncTask<Message, Void, Void>() {

        override fun doInBackground(vararg params: Message): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}

