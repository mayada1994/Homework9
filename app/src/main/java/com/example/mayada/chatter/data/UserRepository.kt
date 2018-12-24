package com.example.mayada.chatter.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.mayada.chatter.data.db.*

class UserRepository internal constructor(application: Application) {

    private val mUserDao: UserDao
    internal val allUsers: LiveData<ArrayList<User>>

    init {
        val db = ChatterDatabase.invoke(application)
        mUserDao = db.userDao()
        allUsers = mUserDao.getAllUsers()
    }


    fun insert(user: User) {
        insertAsyncTask(mUserDao).execute(user)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: UserDao) :
        AsyncTask<User, Void, Void>() {

        override fun doInBackground(vararg params: User): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}

