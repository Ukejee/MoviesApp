package com.ukejee.movieapp.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ukejee.movieapp.data.cache.model.PersistedMovie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<PersistedMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: PersistedMovie)

    @Query("Select * From movies Order By id")
    suspend fun getAllMovies(): List<PersistedMovie>

    @Query("Delete From movies")
    suspend fun deleteAllMovies()

    @Query("Delete From movies WHERE title = :title")
    suspend fun deleteMovie(title: String)
}