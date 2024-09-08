package com.ukejee.movieapp.data

import com.ukejee.movieapp.data.cache.AppDatabase
import com.ukejee.movieapp.data.datasource.MovieLocalDataSource
import com.ukejee.movieapp.data.datasource.MovieRemoteDataSource
import com.ukejee.movieapp.data.datasource.MovieRetrofitDataSource
import com.ukejee.movieapp.data.datasource.MovieRoomDataSource
import com.ukejee.movieapp.data.remote.endpoint.MovieEndpoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDataModule {

    @Provides
    @Singleton
    fun provideMovieEndpoint(retrofit: Retrofit): MovieEndpoint {
        return retrofit.create(MovieEndpoint::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(movieEndpoint: MovieEndpoint): MovieRemoteDataSource {
        return MovieRetrofitDataSource(movieEndpoint)
    }

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(appDatabase: AppDatabase): MovieLocalDataSource {
        return MovieRoomDataSource(appDatabase)
    }
}