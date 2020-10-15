package br.com.rmso.mesanews.repository.remote.feed

import br.com.rmso.mesanews.New
import br.com.rmso.mesanews.Pagination

class FeedUseCase(private val feedRepository: FeedRepository) {
    suspend fun fetchNews(authManager: String, currentPage: Int, perPage: Int): MutableList<New> {
        return this.feedRepository.fetchNews(authManager, currentPage, perPage)
    }

    suspend fun fetchPagination(authManager: String, currentPage: Int, perPage: Int): Pagination {
        return this.feedRepository.fetchPagination(authManager, currentPage, perPage)
    }

    suspend fun fetchHighlights(authManager: String): MutableList<New> {
        return this.feedRepository.fetchHighlights(authManager)
    }

}