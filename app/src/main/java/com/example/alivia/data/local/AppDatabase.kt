package com.example.alivia.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alivia.data.local.dao.ExerciseDao
import com.example.alivia.data.local.dao.SessionDao
import com.example.alivia.data.local.entities.ExerciseEntity
import com.example.alivia.data.local.entities.SessionEntity

@Database(
    entities = [
        SessionEntity::class,
        ExerciseEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sessionDao(): SessionDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "alivia_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
