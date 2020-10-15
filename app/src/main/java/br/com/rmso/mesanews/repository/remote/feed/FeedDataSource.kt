package br.com.rmso.mesanews.repository.remote.feed

import br.com.rmso.mesanews.network.NewsApi

class FeedDataSource(private val newsApi: NewsApi): FeedRepository {

    override suspend fun fetchNews(authManager: String, currentPage: Int, perPage: Int) =
        this.newsApi.getNews(authManager, currentPage, perPage).execute().body()?.listNews?.toMutableList()
            ?: throw NullPointerException("No body")

    override suspend fun fetchPagination(authManager: String, currentPage: Int, perPage: Int) =
        this.newsApi.getNews(authManager, currentPage, perPage).execute().body()?.pagination
            ?: throw NullPointerException("No body")

    override suspend fun fetchHighlights(authManager: String) =
        this.newsApi.getHighlights(authManager).execute().body()?.listNews?.toMutableList()
            ?: throw NullPointerException("No body")

}