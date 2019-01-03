package com.example.mayada.chatter.data

import android.os.AsyncTask
import android.app.Application
import androidx.lifecycle.LiveData

import com.example.mayada.chatter.data.db.ChatterDatabase
import com.example.mayada.chatter.data.db.Message
import com.example.mayada.chatter.data.db.MessageDao


class MessageRepository internal constructor(application: Application) {

    private val mMessageDao: MessageDao
    internal val allMessages: LiveData<List<Message>>

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

        override fun doInBackground(vararg messages: Message): Void? {
            mAsyncTaskDao.insert(messages[0])
            return null
        }
    }

    fun update(message: Message) {
        updateAsyncTask(mMessageDao).execute(message)
    }

    private class updateAsyncTask internal constructor(private val mAsyncTaskDao: MessageDao) :
        AsyncTask<Message, Void, Void>() {

        override fun doInBackground(vararg messages: Message): Void? {
            mAsyncTaskDao.update(messages[0])
            return null
        }
    }

    fun delete(message: Message) {
        deleteAsyncTask(mMessageDao).execute(message)
    }

    private class deleteAsyncTask internal constructor(private val mAsyncTaskDao: MessageDao) :
        AsyncTask<Message, Void, Void>() {

        override fun doInBackground(vararg messages: Message): Void? {
            mAsyncTaskDao.delete(messages[0])
            return null
        }
    }

    fun getAllMessages(): LiveData<List<Message>> {
        return allMessages
    }
}

