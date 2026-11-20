package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        DownloadEntity::class,
        BookmarkEntity::class,
        UserPreferenceEntity::class,
        SupportTicketEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class WinIsoDatabase : RoomDatabase() {
    abstract fun winIsoDao(): WinIsoDao

    companion object {
        @Volatile
        private var INSTANCE: WinIsoDatabase? = null

        fun getDatabase(context: Context): WinIsoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WinIsoDatabase::class.java,
                    "win_iso_database.db"
                ).fallbackToDestructiveMigration(true).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
