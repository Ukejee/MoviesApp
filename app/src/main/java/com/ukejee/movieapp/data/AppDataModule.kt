package com.ukejee.movieapp.data

import android.content.Context
import androidx.room.Room
import com.ukejee.movieapp.data.cache.AppDatabase
import com.ukejee.movieapp.data.remote.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDataModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(converterFactory: Converter.Factory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .readTimeout(ApiConstants.DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(ApiConstants.DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(
                Interceptor { chain ->
                    val request: Request = chain.request()
                        .newBuilder()
                        .header("accept", "application/json")
                        .header("Authorization", "Bearer ${ApiConstants.API_KEY}")
                        .build()
                    chain.proceed(request)
                }
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Singleton
    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }
}