package com.dsl.clima.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PronosticoLocal::class, PronosticoDiarioLocal::class], version = 2)
abstract class PronosticoDatabase : RoomDatabase() {
    abstract val pronosticoDatabaseDao: PronosticoDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: PronosticoDatabase? = null

        fun getDatebase(context: Context): PronosticoDatabase {
            synchronized(this) {
                INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    PronosticoDatabase::class.java,
                    "pronostico_database")
                    .build()
                return INSTANCE!!
            }
        }
    }
}