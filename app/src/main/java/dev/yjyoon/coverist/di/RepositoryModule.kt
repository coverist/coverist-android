package dev.yjyoon.coverist.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.yjyoon.coverist.repository.CoverRepository
import dev.yjyoon.coverist.repository.CoverRepositoryImpl
import dev.yjyoon.coverist.repository.GenreRepository
import dev.yjyoon.coverist.repository.GenreRepositoryImpl
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
}