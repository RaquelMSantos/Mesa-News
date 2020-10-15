package br.com.rmso.mesanews.repository.remote.feed

import br.com.rmso.mesanews.New
import br.com.rmso.mesanews.Pagination

interface FeedRepository {
    suspend fun fetchNews(
        authManager: String,
        currentPage: Int,
        perPage: Int
    ): MutableList<New>

    suspend fun fetchPagination(
        authManager: String,
        currentPage: Int,
        perPage: Int
    ): Pagination

    suspend fun fetchHighlights(authManager: String): MutableList<New>
}