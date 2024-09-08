package com.ukejee.movieapp.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ukejee.movieapp.data.cache.dao.MovieDao
import com.ukejee.movieapp.data.cache.model.PersistedMovie

@Database(
    entities = [PersistedMovie::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}