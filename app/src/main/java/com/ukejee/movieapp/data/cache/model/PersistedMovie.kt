package com.ukejee.movieapp.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ukejee.movieapp.domain.model.Movie

@Entity(tableName = "movies")
data class PersistedMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val releaseDate: String,
    val ratings: String,
    val isRatedR: Boolean,
    val imageUrl: String?,
    val language: String = ""
) {

    fun toDomain(): Movie = Movie(
        title = title,
        description = description,
        releaseDate = releaseDate,
        ratings = ratings,
        isRatedR = isRatedR,
        imageUrl = imageUrl,
        language = language
    )
}
