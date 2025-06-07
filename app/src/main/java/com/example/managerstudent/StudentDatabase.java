package com.example.managerstudent

import static androidx.compose.foundation.text.input.internal.LegacyCursorAnchorInfoBuilder_androidKt.build;

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import kotlin.jvm.Volatile;

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getDatabase(context: Context): StudentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        "StudentDatabase.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}