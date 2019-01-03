package com.example.mayada.chatter.data.db

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class, Message::class], version = 1)
public abstract class ChatterDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var instance: ChatterDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ChatterDatabase::class.java, "chatter.db"
            )
                .addCallback(chatterCallback)
                .build()

        private val chatterCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                ChatterDatabase.PopulateDbAsyncTask(ChatterDatabase.instance!!).execute()
            }
        }
    }

    private class PopulateDbAsyncTask internal constructor(db: ChatterDatabase) : AsyncTask<Void, Void, Void>() {

        private val userDao: UserDao = db.userDao()

        override fun doInBackground(vararg voids: Void): Void? {
            userDao.insert(User(1, "1"))
            userDao.insert(User(2, "2"))
            return null
        }
    }
}