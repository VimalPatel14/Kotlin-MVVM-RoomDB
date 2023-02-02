package com.vimal.mvvmroomdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vimal.mvvmroomdb.dao.EventsDao
import com.vimal.mvvmroomdb.model.Event

@Database(entities = [Event::class], version = 1, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {

    abstract fun getEventsDao(): EventsDao

    companion object {
        // Volatile annotation means any change to this field
        // are immediately visible to other threads.
        @Volatile
        private var INSTANCE: EventDatabase? = null

        private const val DB_NAME = "event_database.db"

        fun getDatabase(context: Context): EventDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            // here synchronised used for blocking the other thread
            // from accessing another while in a specific code execution.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}