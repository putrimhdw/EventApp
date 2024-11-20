package com.caca.eventapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEvent::class], version = 1)
abstract class FavoriteEventDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteEventDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteEventDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteEventDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteEventDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteEventDatabase::class.java, "favorite_user"
                    )
                        .build()
                }
            }
            return INSTANCE as FavoriteEventDatabase
        }
    }
}