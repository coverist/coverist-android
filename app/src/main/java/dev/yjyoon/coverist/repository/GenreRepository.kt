package dev.yjyoon.coverist.repository

import dev.yjyoon.coverist.data.remote.api.GenreService
import dev.yjyoon.coverist.data.remote.model.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GenreRepository {
    suspend fun getGenres(): List<Genre>
    suspend fun getSubGenres(genreId: Int): Flow<List<Genre>>
}

class GenreRepositoryImpl @Inject constructor(
    private val genreService: GenreService
) : GenreRepository {
    override suspend fun getGenres() = genreService.getGenres()

    override suspend fun getSubGenres(genreId: Int): Flow<List<Genre>> = flow {
        val subGenre = genreService.getSubGenres(genreId)
        emit(subGenre)
    }.flowOn(Dispatchers.IO)
}