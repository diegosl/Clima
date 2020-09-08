package com.dsl.clima.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*@Database(entities = [ClimaCache::class], version = 1)
abstract class ClimaDatabase : RoomDatabase() {
    abstract val climaDatabaseDao: ClimaDatabaseDao
}

private lateinit var INSTANCE: ClimaDatabase

fun getDatebase(context: Context): ClimaDatabase {
    synchronized(ClimaDatabase::class.java) {
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, ClimaDatabase::class.java, "clima_history_database").build()
        }
    }
    return INSTANCE
}*/