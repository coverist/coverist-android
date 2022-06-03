package dev.yjyoon.coverist.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.yjyoon.coverist.data.db.BookDatabase
import dev.yjyoon.coverist.data.db.dao.BookDao
import dev.yjyoon.coverist.data.remote.api.CoverService
import dev.yjyoon.coverist.data.remote.api.GenreService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = "http://43.200.53.170:8080/api/v1/"

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: String): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS).build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun genreService(retrofit: Retrofit): GenreService =
        retrofit.create(GenreService::class.java)

    @Singleton
    @Provides
    fun coverService(retrofit: Retrofit): CoverService =
        retrofit.create(CoverService::class.java)

    @Singleton
    @Provides
    fun provideBookDatabase(
        @ApplicationContext context: Context
    ): BookDatabase = Room
        .databaseBuilder(context, BookDatabase::class.java, "book")
        .build()

    @Singleton
    @Provides
    fun provideBookDao(bookDatabase: BookDatabase): BookDao = bookDatabase.bookDao()
}