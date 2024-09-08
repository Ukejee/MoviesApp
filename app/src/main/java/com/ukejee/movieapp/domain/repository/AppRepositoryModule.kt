package com.ukejee.movieapp.domain.repository

import com.ukejee.movieapp.data.datasource.MovieLocalDataSource
import com.ukejee.movieapp.data.datasource.MovieRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppRepositoryModule {

    @Provides
    @Singleton
    fun providesAppRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource
    ): AppRepositoryContract {
        return AppRepository(movieRemoteDataSource, movieLocalDataSource)
    }
}