package dev.yjyoon.coverist.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.yjyoon.coverist.repository.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun genreRepository(repository: GenreRepositoryImpl): GenreRepository

    @Binds
    @Singleton
    abstract fun coverRepository(repository: CoverRepositoryImpl): CoverRepository

    @Binds
    @Singleton
    abstract fun bookRepository(repository: BookRepositoryImpl): BookRepository
}