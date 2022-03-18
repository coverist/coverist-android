package dev.yjyoon.coverist.repository

import android.util.Log
import dev.yjyoon.coverist.data.remote.api.GenreService
import dev.yjyoon.coverist.data.remote.model.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GenreRepository {
    suspend fun getGenres(): Flow<List<Genre>>
    suspend fun getSubGenres(genreId: Int): Flow<List<Genre>>
}

class GenreRepositoryImpl @Inject constructor(
    private val genreService: GenreService
) : GenreRepository {
    override suspend fun getGenres(): Flow<List<Genre>> = flow {
        Log.d("yjy", "loaded genres")
        val genres = genreService.getGenres()
        emit(genres)
    }.flowOn(Dispatchers.IO)

    override suspend fun getSubGenres(genreId: Int): Flow<List<Genre>> = flow {
        Log.d("yjy", "loaded sub genres")
        val subGenre = genreService.getSubGenres(genreId)
        emit(subGenre)
    }.flowOn(Dispatchers.IO)
}